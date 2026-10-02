package com.mopamopa.studio.decorator;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.model.DecorationType;

/**
 * CONCRETE DECORATOR: wraps the finished piece in a handwoven gift box.
 * Being the outermost layer, it must always be applied last.
 */
public class GiftPackagingDecorator extends PieceDecorator {

    private static final DecorationType TYPE = DecorationType.GIFT_PACKAGING;

    public GiftPackagingDecorator(ArtisanPiece wrappedPiece) {
        super(wrappedPiece);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", packed in a handwoven gift box";
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
