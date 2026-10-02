package com.mopamopa.studio.prototype;

import com.mopamopa.studio.model.ArtisanPiece;

import java.util.HashMap;
import java.util.Map;

public class PrototypeRegistry {

    private final Map<String, ArtisanPiecePrototype> prototypes = new HashMap<>();

    public void register(String key, ArtisanPiece piece) {
        prototypes.put(key, new ArtisanPiecePrototype(piece));
    }

    public ArtisanPiece clone(String key) {
        ArtisanPiecePrototype prototype = prototypes.get(key);

        if (prototype == null) {
            return null;
        }

        return prototype.clonePiece();
    }
}
