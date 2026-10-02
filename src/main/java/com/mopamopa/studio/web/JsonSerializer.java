package com.mopamopa.studio.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Minimal JSON writer so the project has no external dependencies.
 * Supports Map, List, String, Number, Boolean and null.
 */
public final class JsonSerializer {

    private JsonSerializer() {
        // Utility class: no instances.
    }

    public static String toJson(Object value) {
        StringBuilder builder = new StringBuilder();
        write(value, builder);
        return builder.toString();
    }

    private static void write(Object value, StringBuilder out) {
        if (value == null) {
            out.append("null");
        } else if (value instanceof String text) {
            writeString(text, out);
        } else if (value instanceof Number || value instanceof Boolean) {
            out.append(value);
        } else if (value instanceof Map<?, ?> map) {
            writeObject(map, out);
        } else if (value instanceof List<?> list) {
            writeArray(list, out);
        } else {
            writeString(value.toString(), out);
        }
    }

    private static void writeObject(Map<?, ?> map, StringBuilder out) {
        out.append('{');
        Iterator<? extends Map.Entry<?, ?>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<?, ?> entry = iterator.next();
            writeString(String.valueOf(entry.getKey()), out);
            out.append(':');
            write(entry.getValue(), out);
            if (iterator.hasNext()) {
                out.append(',');
            }
        }
        out.append('}');
    }

    private static void writeArray(List<?> list, StringBuilder out) {
        out.append('[');
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                out.append(',');
            }
            write(list.get(i), out);
        }
        out.append(']');
    }

    private static void writeString(String text, StringBuilder out) {
        out.append('"');
        for (char c : text.toCharArray()) {
            switch (c) {
                case '"' -> out.append("\\\"");
                case '\\' -> out.append("\\\\");
                case '\n' -> out.append("\\n");
                case '\r' -> out.append("\\r");
                case '\t' -> out.append("\\t");
                default -> {
                    if (c < 0x20) {
                        out.append(String.format("\\u%04x", (int) c));
                    } else {
                        out.append(c);
                    }
                }
            }
        }
        out.append('"');
    }
}
