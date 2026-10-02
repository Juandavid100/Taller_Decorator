package com.mopamopa.studio.factory;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.component.CarnivalMask;

public class CarnivalMaskFactory extends PieceFactory {

    @Override
    public ArtisanPiece createPiece() {
        return new CarnivalMask();
    }
}
