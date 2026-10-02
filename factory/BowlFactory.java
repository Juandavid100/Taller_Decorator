package com.mopamopa.studio.factory;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.component.WoodenBowl;

public class BowlFactory extends PieceFactory {

    @Override
    public ArtisanPiece createPiece() {
        return new WoodenBowl();
    }
}
