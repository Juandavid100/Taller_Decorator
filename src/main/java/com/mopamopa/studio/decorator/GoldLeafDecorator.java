package com.mopamopa.studio.decorator;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.model.DecorationType;

/**
 * CONCRETE DECORATOR: applies gold leaf over the resin ("barniz brillante").
 */
public class GoldLeafDecorator extends PieceDecorator {

    private static final DecorationType TYPE = DecorationType.GOLD_LEAF;

    public GoldLeafDecorator(ArtisanPiece wrappedPiece) {
        super(wrappedPiece);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", gilded with gold leaf";
    }

    @Override
    public long getPrice() {
        return super.getPrice() + TYPE.getExtraPrice();
    }

    @Override
    public int getWorkingDays() {
        return super.getWorkingDays() + TYPE.getExtraDays();
    }
}
