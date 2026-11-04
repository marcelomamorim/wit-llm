/*
 * @test
 * @summary Verifies Locale.setDefault(Locale) rejects null without changing the default locale and accepts a valid locale
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        Locale original = Locale.getDefault();
        try {
            testSetDefaultNullThrowsNPEAndKeepsDefault(original);
            testSetDefaultValidLocale();
        } finally {
            Locale.setDefault(original);
        }
    }

    private static void testSetDefaultNullThrowsNPEAndKeepsDefault(Locale original) {
        Locale.setDefault(original);
        try {
            Locale.setDefault(null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
        if (!Locale.getDefault().equals(original)) {
            throw new RuntimeException("Default locale changed after rejected null: expected "
                    + original + " but was " + Locale.getDefault());
        }
    }

    private static void testSetDefaultValidLocale() {
        Locale fr = new Locale("fr", "CA");
        Locale.setDefault(fr);
        if (!Locale.getDefault().equals(fr)) {
            throw new RuntimeException("Expected default locale " + fr
                    + " but was " + Locale.getDefault());
        }
        Locale de = new Locale("de", "DE");
        Locale.setDefault(de);
        if (!Locale.getDefault().equals(de)) {
            throw new RuntimeException("Expected default locale " + de
                    + " but was " + Locale.getDefault());
        }
    }
}
