package com.mopamopa.studio.model;

/**
 * Catalog of decoration layers. Each constant is the single source of truth for
 * the extra price, the extra working days and the business rules of a layer.
 * The concrete decorators read their values from here.
 */
public enum DecorationType {

    //                  display name                 price    days  repeatable  needsResin  mustBeLast  needsColor
    MOPA_MOPA_LAYER("Mopa-mopa resin layer",         45_000,  3,    true,       false,      false,      true),
    GOLD_LEAF("Gold leaf gilding",                   120_000, 4,    false,      true,       false,      false),
    SILVER_LEAF("Silver leaf gilding",               90_000,  3,    false,      true,       false,      false),
    PROTECTIVE_LACQUER("Protective lacquer",         25_000,  1,    false,      false,      false,      false),
    ARTISAN_SIGNATURE("Master artisan signature",    30_000,  0,    false,      false,      false,      false),
    AUTHENTICITY_CERTIFICATE("Authenticity certificate", 20_000, 1, false,      false,      false,      false),
    GIFT_PACKAGING("Gift packaging",                 15_000,  0,    false,      false,      true,       false);

    private final String displayName;
    private final long extraPrice;
    private final int extraDays;
    private final boolean repeatable;
    private final boolean requiresResinBase;
    private final boolean mustBeLast;
    private final boolean requiresColor;

    DecorationType(String displayName, long extraPrice, int extraDays, boolean repeatable,
                   boolean requiresResinBase, boolean mustBeLast, boolean requiresColor) {
        this.displayName = displayName;
        this.extraPrice = extraPrice;
        this.extraDays = extraDays;
        this.repeatable = repeatable;
        this.requiresResinBase = requiresResinBase;
        this.mustBeLast = mustBeLast;
        this.requiresColor = requiresColor;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getExtraPrice() {
        return extraPrice;
    }

    public int getExtraDays() {
        return extraDays;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public boolean requiresResinBase() {
        return requiresResinBase;
    }

    public boolean mustBeLast() {
        return mustBeLast;
    }

    public boolean requiresColor() {
        return requiresColor;
    }
}
