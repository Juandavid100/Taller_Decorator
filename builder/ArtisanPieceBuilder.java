package com.mopamopa.studio.builder;

import com.mopamopa.studio.model.ArtisanPiece;

public abstract class ArtisanPieceBuilder {

    protected ArtisanPiece piece;

    public ArtisanPieceBuilder() {
        this.piece = new ArtisanPiece();
    }

    public ArtisanPieceBuilder name(String name) {
        piece.setName(name);
        return this;
    }

    public ArtisanPieceBuilder material(String material) {
        piece.setMaterial(material);
        return this;
    }

    public ArtisanPieceBuilder color(String color) {
        piece.setColor(color);
        return this;
    }

    public ArtisanPiece build() {
        return piece;
    }
}
