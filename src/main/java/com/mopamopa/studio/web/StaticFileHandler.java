package com.mopamopa.studio.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Serves the frontend files (HTML, CSS, JS) packaged in the classpath folder "static".
 */
public class StaticFileHandler implements HttpHandler {

    private static final String ROOT = "static";
    private static final Map<String, String> CONTENT_TYPES = Map.of(
            "html", "text/html; charset=utf-8",
            "css", "text/css; charset=utf-8",
            "js", "application/javascript; charset=utf-8",
            "svg", "image/svg+xml",
            "png", "image/png",
            "ico", "image/x-icon");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/") || path.isBlank()) {
                path = "/index.html";
            }
            if (path.contains("..")) {
                sendText(exchange, 400, "Bad request");
                return;
            }
            try (InputStream input = getClass().getClassLoader().getResourceAsStream(ROOT + path)) {
                if (input == null) {
                    sendText(exchange, 404, "Not found");
                    return;
                }
                byte[] bytes = input.readAllBytes();
                exchange.getResponseHeaders().set("Content-Type", contentTypeOf(path));
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream output = exchange.getResponseBody()) {
                    output.write(bytes);
                }
            }
        } finally {
            exchange.close();
        }
    }

    private String contentTypeOf(String path) {
        String extension = path.substring(path.lastIndexOf('.') + 1).toLowerCase();
        return CONTENT_TYPES.getOrDefault(extension, "application/octet-stream");
    }

    private void sendText(HttpExchange exchange, int status, String message) throws IOException {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }
}
