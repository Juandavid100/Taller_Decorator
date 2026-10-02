package com.mopamopa.studio.decorator;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.model.DecorationType;

/**
 * CONCRETE DECORATOR: seals the piece with a transparent protective lacquer.
 */
public class ProtectiveLacquerDecorator extends PieceDecorator {

    private static final DecorationType TYPE = DecorationType.PROTECTIVE_LACQUER;

    public ProtectiveLacquerDecorator(ArtisanPiece wrappedPiece) {
        super(wrappedPiece);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", sealed with protective lacquer";
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
