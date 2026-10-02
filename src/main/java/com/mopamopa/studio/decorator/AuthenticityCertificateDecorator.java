package com.mopamopa.studio.decorator;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.model.DecorationType;

/**
 * CONCRETE DECORATOR: attaches a certificate that guarantees the piece is genuine
 * Pasto Varnish (a craft recognized as Intangible Cultural Heritage by UNESCO).
 */
public class AuthenticityCertificateDecorator extends PieceDecorator {

    private static final DecorationType TYPE = DecorationType.AUTHENTICITY_CERTIFICATE;

    public AuthenticityCertificateDecorator(ArtisanPiece wrappedPiece) {
        super(wrappedPiece);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", with an authenticity certificate";
    }

    @Override
    public long getPrice() {
        return super.getPrice() + TYPE.getExtraPrice();
    }

    @Override
    public int getWorkingDays() {
        return super.getWorkingDays() + TYPE.getExtraDays();
    }
}
