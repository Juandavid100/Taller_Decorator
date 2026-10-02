package com.mopamopa.studio.web;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.model.DecorationType;
import com.mopamopa.studio.model.PieceType;
import com.mopamopa.studio.model.ResinColor;
import com.mopamopa.studio.service.WorkshopCatalog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * GET /api/catalog — lists the base pieces, the decoration layers and the resin colors
 * so the frontend can draw its menus.
 */
public class CatalogHandler extends ApiHandler {

    private final WorkshopCatalog catalog;

    public CatalogHandler(WorkshopCatalog catalog) {
        this.catalog = Objects.requireNonNull(catalog);
    }

    @Override
    protected Object handleGet(Map<String, String> query) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("pieces", pieces());
        body.put("decorations", decorations());
        body.put("colors", colors());
        return body;
    }

    private List<Object> pieces() {
        List<Object> result = new ArrayList<>();
        for (PieceType type : PieceType.values()) {
            ArtisanPiece piece = catalog.createPiece(type);
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", type.name());
            item.put("name", type.getDisplayName());
            item.put("description", piece.getDescription());
            item.put("price", piece.getPrice());
            item.put("days", piece.getWorkingDays());
            result.add(item);
        }
        return result;
    }

    private List<Object> decorations() {
        List<Object> result = new ArrayList<>();
        for (DecorationType type : DecorationType.values()) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", type.name());
            item.put("name", type.getDisplayName());
            item.put("price", type.getExtraPrice());
            item.put("days", type.getExtraDays());
            item.put("repeatable", type.isRepeatable());
            item.put("requiresResinBase", type.requiresResinBase());
            item.put("mustBeLast", type.mustBeLast());
            item.put("requiresColor", type.requiresColor());
            result.add(item);
        }
        return result;
    }

    private List<Object> colors() {
        List<Object> result = new ArrayList<>();
        for (ResinColor color : ResinColor.values()) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", color.name());
            item.put("name", color.getDisplayName());
            item.put("hex", color.getHexCode());
            result.add(item);
        }
        return result;
    }
}
