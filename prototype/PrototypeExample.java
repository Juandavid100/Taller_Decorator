package com.mopamopa.studio.prototype;

import com.mopamopa.studio.model.ArtisanPiece;

public class PrototypeExample {

    public static ArtisanPiece createCopy(
            PrototypeRegistry registry,
            String key) {

        return registry.clone(key);
    }
}
