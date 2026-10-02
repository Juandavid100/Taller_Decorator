package com.mopamopa.studio.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for the JSON endpoints. It handles the HTTP plumbing (method check,
 * query parsing, writing JSON, error responses) so subclasses only implement
 * {@link #handleGet(Map)}.
 */
public abstract class ApiHandler implements HttpHandler {

    @Override
    public final void handle(HttpExchange exchange) throws IOException {
        try {
            if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                sendJson(exchange, 405, Map.of("error", "Only GET is supported"));
                return;
            }
            Map<String, String> query = parseQuery(exchange.getRequestURI().getRawQuery());
            sendJson(exchange, 200, handleGet(query));
        } catch (IllegalArgumentException exception) {
            sendJson(exchange, 400, Map.of("error", exception.getMessage()));
        } catch (RuntimeException exception) {
            sendJson(exchange, 500, Map.of("error", "Unexpected server error"));
        } finally {
            exchange.close();
        }
    }

    /**
     * @param query the decoded query string parameters
     * @return a Map/List structure that will be serialized to JSON
     * @throws IllegalArgumentException if the request is invalid (answered with HTTP 400)
     */
    protected abstract Object handleGet(Map<String, String> query);

    private void sendJson(HttpExchange exchange, int status, Object body) throws IOException {
        byte[] bytes = JsonSerializer.toJson(body).getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }

    private Map<String, String> parseQuery(String rawQuery) {
        Map<String, String> result = new HashMap<>();
        if (rawQuery == null || rawQuery.isBlank()) {
            return result;
        }
        for (String pair : rawQuery.split("&")) {
            int separator = pair.indexOf('=');
            String key = separator >= 0 ? pair.substring(0, separator) : pair;
            String value = separator >= 0 ? pair.substring(separator + 1) : "";
            result.put(URLDecoder.decode(key, StandardCharsets.UTF_8),
                    URLDecoder.decode(value, StandardCharsets.UTF_8));
        }
        return result;
    }
}
