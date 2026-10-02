package com.mopamopa.studio.decorator;

import com.mopamopa.studio.component.ArtisanPiece;

import java.util.Objects;

/**
 * ABSTRACT DECORATOR (Decorator pattern).
 * <p>
 * Implements {@link ArtisanPiece} and holds a reference to another
 * {@link ArtisanPiece} (which may be a plain piece or another decorator).
 * By default it delegates every call to the wrapped piece; concrete decorators
 * call {@code super} and add their own behavior on top.
 * <p>
 * This is composition instead of inheritance: decorations can be stacked
 * at runtime in any order and combination without creating subclasses
 * such as "BowlWithGoldAndLacquer".
 */
public abstract class PieceDecorator implements ArtisanPiece {

    private final ArtisanPiece wrappedPiece;

    protected PieceDecorator(ArtisanPiece wrappedPiece) {
        this.wrappedPiece = Objects.requireNonNull(wrappedPiece, "The piece to decorate is required");
    }

    protected final ArtisanPiece getWrappedPiece() {
        return wrappedPiece;
    }

    @Override
    public String getDescription() {
        return wrappedPiece.getDescription();
    }

    @Override
    public long getPrice() {
        return wrappedPiece.getPrice();
    }

    @Override
    public int getWorkingDays() {
        return wrappedPiece.getWorkingDays();
    }
}
