/*
 * @test
 * @summary Test getDisplayName method of ThaiBuddhistEra
 * @run main ThaiBuddhistEraWitupTest
 */

import java.time.chrono.ThaiBuddhistEra;
import java.time.chrono.ThaiBuddhistDate;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.format.TextStyle;
import java.util.Locale;

public class ThaiBuddhistEraWitupTest {
    public static void main(String[] args) {
        testGetDisplayNameBE();
        testGetDisplayNameBeforeBE();
        testGetDisplayNameDifferentStylesAndLocales();
    }

    static void testGetDisplayNameBE() {
        ThaiBuddhistEra era = ThaiBuddhistEra.BE;
        String name = era.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("Display name for BE era should not be null or empty");
        }
    }

    static void testGetDisplayNameBeforeBE() {
        ThaiBuddhistEra era = ThaiBuddhistEra.BE == null ? null : (ThaiBuddhistEra) (ThaiBuddhistEra.BE == ThaiBuddhistEra.BE ? ThaiBuddhistEra.BE : null); // workaround to get BeforeBE
        // Actually, ThaiBuddhistEra has two constants: BE and BEFORE_BE
        // We test BEFORE_BE by using ThaiBuddhistEra.of(0) which returns BEFORE_BE
        era = ThaiBuddhistEra.of(0);
        String name = era.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("Display name for BEFORE_BE era should not be null or empty");
        }
    }

    static void testGetDisplayNameDifferentStylesAndLocales() {
        ThaiBuddhistEra eraBE = ThaiBuddhistEra.BE;
        ThaiBuddhistEra eraBeforeBE = ThaiBuddhistEra.of(0);

        TextStyle[] styles = {TextStyle.FULL, TextStyle.SHORT, TextStyle.NARROW};
        Locale[] locales = {Locale.ENGLISH, Locale.FRENCH, Locale.JAPANESE};

        for (TextStyle style : styles) {
            for (Locale locale : locales) {
                String nameBE = eraBE.getDisplayName(style, locale);
                if (nameBE == null || nameBE.isEmpty()) {
                    throw new AssertionError("Display name for BE era with style " + style + " and locale " + locale + " should not be null or empty");
                }
                String nameBeforeBE = eraBeforeBE.getDisplayName(style, locale);
                if (nameBeforeBE == null || nameBeforeBE.isEmpty()) {
                    throw new AssertionError("Display name for BEFORE_BE era with style " + style + " and locale " + locale + " should not be null or empty");
                }
            }
        }
    }
}
