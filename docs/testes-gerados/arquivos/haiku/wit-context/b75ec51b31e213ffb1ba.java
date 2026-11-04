/*
 * @test
 * @summary Witup-derived checks for Locale.Builder.setLanguageTag behavior on valid and ill-formed tags
 * @run main LocaleWitupTest
 */

import java.util.IllformedLocaleException;
import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testValidLanguageTag();
        testIllformedLanguageTagThrows();
    }

    static void testValidLanguageTag() {
        Locale l = new Locale.Builder().setLanguageTag("en-US").build();
        if (!"en".equals(l.getLanguage())) {
            throw new RuntimeException("Expected language 'en' but got: " + l.getLanguage());
        }
        if (!"US".equals(l.getCountry())) {
            throw new RuntimeException("Expected country 'US' but got: " + l.getCountry());
        }
        if (!"en-US".equals(l.toLanguageTag())) {
            throw new RuntimeException("Expected tag 'en-US' but got: " + l.toLanguageTag());
        }
    }

    static void testIllformedLanguageTagThrows() {
        String[] illformed = { "en_US", "a" };
        for (String tag : illformed) {
            boolean thrown = false;
            try {
                new Locale.Builder().setLanguageTag(tag);
            } catch (IllformedLocaleException e) {
                thrown = true;
                if (e.getErrorIndex() < 0) {
                    throw new RuntimeException("Expected non-negative error index for tag '"
                            + tag + "' but got: " + e.getErrorIndex());
                }
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllformedLocaleException for tag: " + tag);
            }
        }
    }
}
