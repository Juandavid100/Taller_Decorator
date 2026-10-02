package com.mopamopa.studio.model;

/**
 * Colors available for the mopa-mopa resin. The resin is extracted from the
 * Elaeagia pastoensis shrub and dyed with natural or mineral pigments.
 */
public enum ResinColor {

    RED("red", "#c62f2f"),
    GREEN("green", "#2f8f4e"),
    BLUE("blue", "#2c5fb3"),
    YELLOW("yellow", "#e8b923"),
    BLACK("black", "#1d1b1a"),
    WHITE("white", "#f2ede4");

    private final String displayName;
    private final String hexCode;

    ResinColor(String displayName, String hexCode) {
        this.displayName = displayName;
        this.hexCode = hexCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getHexCode() {
        return hexCode;
    }
}
