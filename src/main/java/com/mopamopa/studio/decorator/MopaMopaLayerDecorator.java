package com.mopamopa.studio.decorator;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.model.DecorationType;
import com.mopamopa.studio.model.ResinColor;

import java.util.Objects;

/**
 * CONCRETE DECORATOR: adds a layer of dyed mopa-mopa resin.
 * The artisan chews and stretches the resin into a thin film and cuts it
 * into figures that are pressed onto the piece. It can be applied several
 * times with different colors.
 */
public class MopaMopaLayerDecorator extends PieceDecorator {

    private static final DecorationType TYPE = DecorationType.MOPA_MOPA_LAYER;

    private final ResinColor color;

    public MopaMopaLayerDecorator(ArtisanPiece wrappedPiece, ResinColor color) {
        super(wrappedPiece);
        this.color = Objects.requireNonNull(color, "The resin color is required");
    }

    public ResinColor getColor() {
        return color;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", with a " + color.getDisplayName() + " mopa-mopa resin layer";
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
