package com.mopamopa.studio.web;

import com.mopamopa.studio.model.DecorationType;
import com.mopamopa.studio.model.PieceType;
import com.mopamopa.studio.model.ResinColor;
import com.mopamopa.studio.service.DecorationRequest;
import com.mopamopa.studio.service.InvalidOrderException;
import com.mopamopa.studio.service.Quote;
import com.mopamopa.studio.service.QuoteService;
import com.mopamopa.studio.service.QuoteStep;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * GET /api/quote?piece=BOWL&amp;layers=MOPA_MOPA_LAYER:RED,GOLD_LEAF,GIFT_PACKAGING
 * <p>
 * Builds the decorated piece with {@link QuoteService} and returns the quote as JSON.
 * The order of {@code layers} is the order in which the decorators wrap the piece.
 */
public class QuoteHandler extends ApiHandler {

    private final QuoteService quoteService;

    public QuoteHandler(QuoteService quoteService) {
        this.quoteService = Objects.requireNonNull(quoteService);
    }

    @Override
    protected Object handleGet(Map<String, String> query) {
        PieceType pieceType = parseEnum(PieceType.class, query.get("piece"), "piece");
        List<DecorationRequest> requests = parseLayers(query.getOrDefault("layers", ""));
        try {
            return toResponse(quoteService.quote(pieceType, requests));
        } catch (InvalidOrderException exception) {
            throw new IllegalArgumentException(exception.getMessage(), exception);
        }
    }

    private List<DecorationRequest> parseLayers(String rawLayers) {
        List<DecorationRequest> requests = new ArrayList<>();
        if (rawLayers.isBlank()) {
            return requests;
        }
        for (String token : rawLayers.split(",")) {
            String[] parts = token.trim().split(":");
            DecorationType type = parseEnum(DecorationType.class, parts[0], "layer");
            ResinColor color = parts.length > 1 ? parseEnum(ResinColor.class, parts[1], "color") : null;
            requests.add(new DecorationRequest(type, color));
        }
        return requests;
    }

    private <E extends Enum<E>> E parseEnum(Class<E> enumClass, String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing " + fieldName);
        }
        try {
            return Enum.valueOf(enumClass, value.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unknown " + fieldName + ": " + value);
        }
    }

    private Map<String, Object> toResponse(Quote quote) {
        List<Object> steps = new ArrayList<>();
        for (QuoteStep step : quote.getSteps()) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("layer", step.getLayerName());
            item.put("className", step.getClassName());
            item.put("colorHex", step.getColorHex());
            item.put("addedPrice", step.getAddedPrice());
            item.put("addedDays", step.getAddedDays());
            item.put("accumulatedPrice", step.getAccumulatedPrice());
            item.put("accumulatedDays", step.getAccumulatedDays());
            steps.add(item);
        }
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("description", quote.getDescription());
        body.put("totalPrice", quote.getTotalPrice());
        body.put("totalDays", quote.getTotalWorkingDays());
        body.put("javaExpression", quote.getJavaExpression());
        body.put("steps", steps);
        return body;
    }
}
