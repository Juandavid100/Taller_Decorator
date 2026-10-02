package com.mopamopa.studio.builder;

import com.mopamopa.studio.model.ArtisanPiece;

public class ArtisanPieceDirector {

    public ArtisanPiece createStandardPiece(
            String name,
            String material,
            String color) {

        return new StandardArtisanPieceBuilder()
                .name(name)
                .material(material)
                .color(color)
                .build();
    }
}
