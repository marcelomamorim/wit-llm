/*
 * @test
 * @summary Test getISO3Country() method of java.util.Locale for expected behavior and exception
 * @run main LocaleWitupTest
 */

import java.util.Locale;
import java.util.MissingResourceException;

public class LocaleWitupTest {
    public static void main(String[] args) {
        testValidCountry();
        testMissingResourceException();
        System.out.println("LocaleWitupTest passed.");
    }

    private static void testValidCountry() {
        // ISO 3166 alpha-2 country code "US" should map to "USA"
        Locale locale = new Locale("en", "US");
        String iso3Country = locale.getISO3Country();
        if (!"USA".equals(iso3Country)) {
            throw new AssertionError("Expected ISO3 country code 'USA' but got '" + iso3Country + "'");
        }
    }

    private static void testMissingResourceException() {
        // Construct a Locale with an invalid country code that is not in ISO country table
        // According to the source, getISO3Country() should throw MissingResourceException
        Locale locale = new Locale("en", "ZZ");
        try {
            locale.getISO3Country();
            throw new AssertionError("Expected MissingResourceException for invalid country code 'ZZ'");
        } catch (MissingResourceException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("ZZ")) {
                throw new AssertionError("Exception message does not contain invalid country code: " + msg);
            }
        }
    }
}
