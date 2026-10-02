package com.mopamopa.studio.component;

/**
 * COMPONENT (Decorator pattern).
 * <p>
 * Common contract for every handcrafted piece sold by the workshop.
 * Plain wooden pieces and every decoration layer implement this interface,
 * so the client can treat a decorated piece exactly like an undecorated one.
 */
public interface ArtisanPiece {

    /** @return a human readable description of the piece and all its layers. */
    String getDescription();

    /** @return the total price in Colombian pesos (COP). */
    long getPrice();

    /** @return the total number of artisan working days needed to finish the piece. */
    int getWorkingDays();
}
