package com.mopamopa.studio.component;

/**
 * Abstract base class for the undecorated wooden pieces.
 * <p>
 * It keeps the shared state (name, wood type, base price and base working days)
 * encapsulated and validated in a single place, so every concrete piece only has
 * to declare its own values.
 */
public abstract class WoodenPiece implements ArtisanPiece {

    private final String name;
    private final String woodType;
    private final long basePrice;
    private final int baseWorkingDays;

    protected WoodenPiece(String name, String woodType, long basePrice, int baseWorkingDays) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The piece name is required");
        }
        if (woodType == null || woodType.isBlank()) {
            throw new IllegalArgumentException("The wood type is required");
        }
        if (basePrice <= 0) {
            throw new IllegalArgumentException("The base price must be positive");
        }
        if (baseWorkingDays <= 0) {
            throw new IllegalArgumentException("The base working days must be positive");
        }
        this.name = name;
        this.woodType = woodType;
        this.basePrice = basePrice;
        this.baseWorkingDays = baseWorkingDays;
    }

    @Override
    public String getDescription() {
        return name + " carved in " + woodType + " wood";
    }

    @Override
    public long getPrice() {
        return basePrice;
    }

    @Override
    public int getWorkingDays() {
        return baseWorkingDays;
    }

    public String getName() {
        return name;
    }

    public String getWoodType() {
        return woodType;
    }
}
