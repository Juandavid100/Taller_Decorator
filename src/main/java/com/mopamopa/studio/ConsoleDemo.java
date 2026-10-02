package com.mopamopa.studio;

import com.mopamopa.studio.component.ArtisanPiece;
import com.mopamopa.studio.component.CarnivalMask;
import com.mopamopa.studio.component.WoodenBowl;
import com.mopamopa.studio.decorator.ArtisanSignatureDecorator;
import com.mopamopa.studio.decorator.GiftPackagingDecorator;
import com.mopamopa.studio.decorator.GoldLeafDecorator;
import com.mopamopa.studio.decorator.MopaMopaLayerDecorator;
import com.mopamopa.studio.decorator.ProtectiveLacquerDecorator;
import com.mopamopa.studio.model.ResinColor;

import java.util.Locale;

/**
 * Console version of the example, in the same style as the class example
 * (MusicPlayer / EqualizerDecorator). Run with: {@code Main --console}.
 */
public final class ConsoleDemo {

    private ConsoleDemo() {
        // Utility class: no instances.
    }

    public static void run() {
        // 1. Plain piece, without decorators
        ArtisanPiece simpleBowl = new WoodenBowl();
        print("Plain piece:", simpleBowl);

        // 2. Same kind of piece, wrapped by several decorators at runtime
        ArtisanPiece decoratedBowl =
                new GiftPackagingDecorator(
                        new GoldLeafDecorator(
                                new MopaMopaLayerDecorator(
                                        new MopaMopaLayerDecorator(new WoodenBowl(), ResinColor.RED),
                                        ResinColor.GREEN)));
        print("Decorated bowl:", decoratedBowl);

        // 3. Decorators are added step by step: the variable keeps the same type
        ArtisanPiece mask = new CarnivalMask();
        mask = new MopaMopaLayerDecorator(mask, ResinColor.YELLOW);
        mask = new ProtectiveLacquerDecorator(mask);
        mask = new ArtisanSignatureDecorator(mask);
        print("Decorated carnival mask:", mask);
    }

    private static void print(String title, ArtisanPiece piece) {
        System.out.println(title);
        System.out.println("  Description : " + piece.getDescription());
        System.out.println("  Price       : " + formatPesos(piece.getPrice()));
        System.out.println("  Working days: " + piece.getWorkingDays());
        System.out.println();
    }

    /** Formats a price as Colombian pesos, e.g. 310000 becomes "$310.000 COP". */
    private static String formatPesos(long price) {
        return String.format(Locale.US, "$%,d COP", price).replace(',', '.');
    }
}
