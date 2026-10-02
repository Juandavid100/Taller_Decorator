package com.mopamopa.studio;

import com.mopamopa.studio.service.QuoteService;
import com.mopamopa.studio.service.WorkshopCatalog;
import com.mopamopa.studio.web.WebServer;

import java.io.IOException;

/**
 * Entry point.
 * <ul>
 *     <li>No arguments: starts the web application at http://localhost:8080</li>
 *     <li>{@code --console}: runs the console demo of the Decorator pattern</li>
 *     <li>{@code --port=9090}: starts the web application on another port</li>
 * </ul>
 * In the cloud (Render, Railway, etc.) the port is read from the {@code PORT}
 * environment variable.
 */
public final class Main {

    private static final int DEFAULT_PORT = 8080;

    private Main() {
        // Entry point class: no instances.
    }

    public static void main(String[] args) throws IOException {
        int port = readPortFromEnvironment();
        for (String argument : args) {
            if ("--console".equals(argument)) {
                ConsoleDemo.run();
                return;
            }
            if (argument.startsWith("--port=")) {
                port = Integer.parseInt(argument.substring("--port=".length()));
            }
        }

        WorkshopCatalog catalog = new WorkshopCatalog();
        QuoteService quoteService = new QuoteService(catalog);
        WebServer server = new WebServer(port, catalog, quoteService);
        server.start();

        System.out.println("Mopa-Mopa Studio is running at http://localhost:" + port);
        System.out.println("Press Ctrl+C to stop the server.");
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }

    private static int readPortFromEnvironment() {
        String value = System.getenv("PORT");
        if (value == null || value.isBlank()) {
            return DEFAULT_PORT;
        }
        return Integer.parseInt(value.trim());
    }
}
