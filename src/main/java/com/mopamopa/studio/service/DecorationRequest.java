package com.mopamopa.studio.service;

import com.mopamopa.studio.model.DecorationType;
import com.mopamopa.studio.model.ResinColor;

import java.util.Objects;

/**
 * One decoration layer requested by the customer, in the order it must be applied.
 */
public class DecorationRequest {

    private final DecorationType type;
    private final ResinColor color;

    public DecorationRequest(DecorationType type, ResinColor color) {
        this.type = Objects.requireNonNull(type, "The decoration type is required");
        this.color = color;
    }

    public DecorationRequest(DecorationType type) {
        this(type, null);
    }

    public DecorationType getType() {
        return type;
    }

    public ResinColor getColor() {
        return color;
    }
}
