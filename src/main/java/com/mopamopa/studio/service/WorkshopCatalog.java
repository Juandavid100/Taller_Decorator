package com.mopamopa.studio.service;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.component.CarnivalMask;
import com.mopamopa.studio.component.DecorativePlate;
import com.mopamopa.studio.component.JewelryBox;
import com.mopamopa.studio.component.WoodenBowl;
import com.mopamopa.studio.decorator.ArtisanSignatureDecorator;
import com.mopamopa.studio.decorator.AuthenticityCertificateDecorator;
import com.mopamopa.studio.decorator.GiftPackagingDecorator;
import com.mopamopa.studio.decorator.GoldLeafDecorator;
import com.mopamopa.studio.decorator.MopaMopaLayerDecorator;
import com.mopamopa.studio.decorator.ProtectiveLacquerDecorator;
import com.mopamopa.studio.decorator.SilverLeafDecorator;
import com.mopamopa.studio.model.DecorationType;
import com.mopamopa.studio.model.PieceType;
import com.mopamopa.studio.model.ResinColor;

/**
 * Translates the identifiers that arrive from the frontend into real objects:
 * it creates the base pieces and wraps them with the requested decorator.
 */
public class WorkshopCatalog {

    public ArtisanPiece createPiece(PieceType type) {
        return switch (type) {
            case BOWL -> new WoodenBowl();
            case JEWELRY_BOX -> new JewelryBox();
            case DECORATIVE_PLATE -> new DecorativePlate();
            case CARNIVAL_MASK -> new CarnivalMask();
        };
    }

    /**
     * Wraps {@code piece} with the decorator that matches {@code type}.
     *
     * @param color only used by {@link DecorationType#MOPA_MOPA_LAYER}; ignored otherwise.
     */
    public ArtisanPiece decorate(ArtisanPiece piece, DecorationType type, ResinColor color) {
        return switch (type) {
            case MOPA_MOPA_LAYER -> new MopaMopaLayerDecorator(piece, color);
            case GOLD_LEAF -> new GoldLeafDecorator(piece);
            case SILVER_LEAF -> new SilverLeafDecorator(piece);
            case PROTECTIVE_LACQUER -> new ProtectiveLacquerDecorator(piece);
            case ARTISAN_SIGNATURE -> new ArtisanSignatureDecorator(piece);
            case AUTHENTICITY_CERTIFICATE -> new AuthenticityCertificateDecorator(piece);
            case GIFT_PACKAGING -> new GiftPackagingDecorator(piece);
        };
    }
}
