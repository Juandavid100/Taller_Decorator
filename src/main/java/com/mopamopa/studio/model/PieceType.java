package com.mopamopa.studio.model;

/**
 * Identifiers of the base wooden pieces offered by the workshop.
 */
public enum PieceType {

    BOWL("Wooden bowl"),
    JEWELRY_BOX("Jewelry box"),
    DECORATIVE_PLATE("Decorative plate"),
    CARNIVAL_MASK("Carnival mask");

    private final String displayName;

    PieceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
