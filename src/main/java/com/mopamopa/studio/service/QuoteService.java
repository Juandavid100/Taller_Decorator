package com.mopamopa.studio.service;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.model.DecorationType;
import com.mopamopa.studio.model.PieceType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Builds a decorated piece layer by layer and validates the workshop rules:
 * <ul>
 *     <li>Gold or silver leaf can only be applied over a mopa-mopa resin layer.</li>
 *     <li>Only the mopa-mopa layer can be repeated.</li>
 *     <li>Gift packaging must be the last (outermost) layer.</li>
 *     <li>The mopa-mopa layer needs a color.</li>
 *     <li>An order cannot have more than {@value #MAX_LAYERS} layers.</li>
 * </ul>
 */
public class QuoteService {

    public static final int MAX_LAYERS = 10;

    private final WorkshopCatalog catalog;

    public QuoteService(WorkshopCatalog catalog) {
        this.catalog = Objects.requireNonNull(catalog);
    }

    public Quote quote(PieceType pieceType, List<DecorationRequest> requests) {
        Objects.requireNonNull(pieceType, "The piece type is required");
        Objects.requireNonNull(requests, "The decoration list is required");
        if (requests.size() > MAX_LAYERS) {
            throw new InvalidOrderException("An order can have at most " + MAX_LAYERS + " layers");
        }

        ArtisanPiece piece = catalog.createPiece(pieceType);
        List<QuoteStep> steps = new ArrayList<>();
        steps.add(new QuoteStep(pieceType.getDisplayName(), piece.getClass().getSimpleName(), null,
                piece.getPrice(), piece.getWorkingDays(), piece.getPrice(), piece.getWorkingDays()));
        String expression = "new " + piece.getClass().getSimpleName() + "()";

        Set<DecorationType> applied = EnumSet.noneOf(DecorationType.class);
        for (int i = 0; i < requests.size(); i++) {
            DecorationRequest request = requests.get(i);
            validate(request, applied, i == requests.size() - 1);

            long previousPrice = piece.getPrice();
            int previousDays = piece.getWorkingDays();
            piece = catalog.decorate(piece, request.getType(), request.getColor());
            applied.add(request.getType());

            String className = piece.getClass().getSimpleName();
            String colorArgument = request.getColor() != null && request.getType().requiresColor()
                    ? ", ResinColor." + request.getColor().name() : "";
            expression = "new " + className + "(" + expression + colorArgument + ")";

            steps.add(new QuoteStep(layerName(request), className,
                    request.getType().requiresColor() ? request.getColor().getHexCode() : null,
                    piece.getPrice() - previousPrice, piece.getWorkingDays() - previousDays,
                    piece.getPrice(), piece.getWorkingDays()));
        }
        return new Quote(piece, steps, expression);
    }

    private void validate(DecorationRequest request, Set<DecorationType> applied, boolean isLast) {
        DecorationType type = request.getType();
        if (type.requiresColor() && request.getColor() == null) {
            throw new InvalidOrderException(type.getDisplayName() + " needs a resin color");
        }
        if (!type.isRepeatable() && applied.contains(type)) {
            throw new InvalidOrderException(type.getDisplayName() + " can only be applied once");
        }
        if (type.requiresResinBase() && !applied.contains(DecorationType.MOPA_MOPA_LAYER)) {
            throw new InvalidOrderException(type.getDisplayName()
                    + " must be applied over a mopa-mopa resin layer");
        }
        if (type.mustBeLast() && !isLast) {
            throw new InvalidOrderException(type.getDisplayName() + " must be the last layer");
        }
    }

    private String layerName(DecorationRequest request) {
        DecorationType type = request.getType();
        if (type.requiresColor()) {
            return type.getDisplayName() + " (" + request.getColor().getDisplayName() + ")";
        }
        return type.getDisplayName();
    }
}
