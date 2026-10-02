package com.mopamopa.studio.factory;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.component.DecorativePlate;

public class DecorativePlateFactory extends PieceFactory {

    @Override
    public ArtisanPiece createPiece() {
        return new DecorativePlate();
    }
}
