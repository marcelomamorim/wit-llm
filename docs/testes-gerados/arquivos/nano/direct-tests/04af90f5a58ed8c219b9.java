/*
 * @test    @summary Test Locale.getISO3Country() for valid and invalid regions
 * @run main LocaleWitupTest
 */

import java.util.Locale;
import java.util.MissingResourceException;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testValidRegion();
        testInvalidRegion();
    }

    private static void testValidRegion() {
        Locale locale = new Locale("en", "US");
        try {
            String iso3 = locale.getISO3Country();
            System.out.println("ISO3 country code for US: " + iso3);
            // Expect a non-null, 3-letter code
            assert iso3 != null : "ISO3 code should not be null for US";
            assert iso3.length() == 3 : "ISO3 code should be 3 characters";
        } catch (MissingResourceException e) {
            throw new AssertionError("Unexpected MissingResourceException for valid region", e);
        }
    }

    private static void testInvalidRegion() {
        Locale locale = new Locale("en", "ZZZZ");
        try {
            locale.getISO3Country();
            throw new AssertionError("Expected MissingResourceException for invalid region");
        } catch (MissingResourceException e) {
            System.out.println("Caught expected MissingResourceException for invalid region: " + e.getMessage());
        }
    }
}
