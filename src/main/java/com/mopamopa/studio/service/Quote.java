package com.mopamopa.studio.service;

import com.mopamopa.studio.component.ArtisanPiece;

import java.util.List;
import java.util.Objects;

/**
 * Result of quoting an order: the fully decorated piece plus a step by step
 * breakdown and the equivalent Java expression that builds it.
 */
public class Quote {

    private final ArtisanPiece piece;
    private final List<QuoteStep> steps;
    private final String javaExpression;

    public Quote(ArtisanPiece piece, List<QuoteStep> steps, String javaExpression) {
        this.piece = Objects.requireNonNull(piece);
        this.steps = List.copyOf(steps);
        this.javaExpression = javaExpression;
    }

    public String getDescription() {
        return piece.getDescription();
    }

    public long getTotalPrice() {
        return piece.getPrice();
    }

    public int getTotalWorkingDays() {
        return piece.getWorkingDays();
    }

    public List<QuoteStep> getSteps() {
        return steps;
    }

    public String getJavaExpression() {
        return javaExpression;
    }
}
