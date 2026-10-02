package com.mopamopa.studio.service;

/**
 * Snapshot of the order after one layer has been applied.
 * The difference between two consecutive steps is what that layer added.
 */
public class QuoteStep {

    private final String layerName;
    private final String className;
    private final String colorHex;
    private final long addedPrice;
    private final int addedDays;
    private final long accumulatedPrice;
    private final int accumulatedDays;

    public QuoteStep(String layerName, String className, String colorHex, long addedPrice,
                     int addedDays, long accumulatedPrice, int accumulatedDays) {
        this.layerName = layerName;
        this.className = className;
        this.colorHex = colorHex;
        this.addedPrice = addedPrice;
        this.addedDays = addedDays;
        this.accumulatedPrice = accumulatedPrice;
        this.accumulatedDays = accumulatedDays;
    }

    public String getLayerName() {
        return layerName;
    }

    public String getClassName() {
        return className;
    }

    public String getColorHex() {
        return colorHex;
    }

    public long getAddedPrice() {
        return addedPrice;
    }

    public int getAddedDays() {
        return addedDays;
    }

    public long getAccumulatedPrice() {
        return accumulatedPrice;
    }

    public int getAccumulatedDays() {
        return accumulatedDays;
    }
}
