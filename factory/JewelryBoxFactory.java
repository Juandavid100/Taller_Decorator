package com.mopamopa.studio.factory;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.component.JewelryBox;

public class JewelryBoxFactory extends PieceFactory {

    @Override
    public ArtisanPiece createPiece() {
        return new JewelryBox();
    }
}
