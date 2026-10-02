package com.mopamopa.studio.decorator;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.model.DecorationType;

/**
 * CONCRETE DECORATOR: the master artisan engraves his or her signature on the piece.
 */
public class ArtisanSignatureDecorator extends PieceDecorator {

    private static final DecorationType TYPE = DecorationType.ARTISAN_SIGNATURE;

    public ArtisanSignatureDecorator(ArtisanPiece wrappedPiece) {
        super(wrappedPiece);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", signed by the master artisan";
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
