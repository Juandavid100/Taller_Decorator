package com.mopamopa.studio.builder;

import com.mopamopa.studio.model.ArtisanPiece;

public class BuilderExample {

    public static ArtisanPiece createExample() {
        ArtisanPieceDirector director = new ArtisanPieceDirector();

        return director.createStandardPiece(
                "Pieza artesanal",
                "Mopa-Mopa",
                "Multicolor"
        );
    }
}
