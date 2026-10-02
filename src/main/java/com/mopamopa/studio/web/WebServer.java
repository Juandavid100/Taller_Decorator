package com.mopamopa.studio.web;

import com.mopamopa.studio.service.QuoteService;
import com.mopamopa.studio.service.WorkshopCatalog;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Embedded HTTP server (JDK built-in, no external libraries) that exposes the
 * REST API and serves the frontend.
 */
public class WebServer {

    private final int port;
    private final WorkshopCatalog catalog;
    private final QuoteService quoteService;
    private HttpServer server;

    public WebServer(int port, WorkshopCatalog catalog, QuoteService quoteService) {
        this.port = port;
        this.catalog = catalog;
        this.quoteService = quoteService;
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/api/catalog", new CatalogHandler(catalog));
        server.createContext("/api/quote", new QuoteHandler(quoteService));
        server.createContext("/", new StaticFileHandler());
        server.start();
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    public int getPort() {
        return port;
    }
}
