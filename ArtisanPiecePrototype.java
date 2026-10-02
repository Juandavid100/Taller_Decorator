package com.mopamopa.studio.prototype;

import com.mopamopa.studio.model.ArtisanPiece;

public class ArtisanPiecePrototype implements Cloneable {

    private ArtisanPiece prototype;

    public ArtisanPiecePrototype(ArtisanPiece prototype) {
        this.prototype = prototype;
    }

    public ArtisanPiece clonePiece() {
        ArtisanPiece copy = new ArtisanPiece();

        copy.setName(prototype.getName());
        copy.setMaterial(prototype.getMaterial());
        copy.setColor(prototype.getColor());

        return copy;
    }
}
