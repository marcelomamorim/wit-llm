/*
 * @test
 * @summary Test for Locale.setLanguageTag(String)
 * @run main LocaleWitupTest
 */

import java.util.Locale;
import java.util.spi.LocaleNameProvider;
import java.text.ParseException;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testValidLanguageTag();
        testInvalidLanguageTag();
    }

    static void testValidLanguageTag() {
        Locale.Builder builder = new Locale.Builder();
        String validTag = "en-US";
        try {
            builder.setLanguageTag(validTag);
            Locale locale = builder.build();
            assert locale != null : "Locale should not be null for valid tag";
            assert locale.getLanguage().equals("en") : "Expected language 'en'";
            assert locale.getCountry().equals("US") : "Expected country 'US'";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid tag", e);
        }
    }

    static void testInvalidLanguageTag() {
        Locale.Builder builder = new Locale.Builder();
        String invalidTag = "!!!invalid!!!";
        try {
            builder.setLanguageTag(invalidTag);
            throw new AssertionError("Expected IllformedLocaleException for invalid tag");
        } catch (IllformedLocaleException e) {
            // Expected exception
            assert e.getMessage() != null : "Exception message should not be null";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
