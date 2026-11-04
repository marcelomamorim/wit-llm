/*
 * @test
 * @summary Test for Locale.getISO3Country() method, focusing on normal and exceptional behavior.
 * @run main LocaleWitupTest
 */

import java.util.Locale;
import java.util.MissingResourceException;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetISO3CountryValidRegion();
        testGetISO3CountryEmptyRegion();
        testGetISO3CountryInvalidRegion();
    }

    private static void testGetISO3CountryValidRegion() {
        Locale locale = new Locale("en", "US");
        try {
            String iso3 = locale.getISO3Country();
            System.out.println("Valid region test: " + iso3);
            // Expect a valid 3-letter country code, e.g., "USA"
            assert iso3 != null && iso3.length() == 3 : "Expected 3-letter country code";
        } catch (MissingResourceException e) {
            throw new AssertionError("Unexpected MissingResourceException for valid region", e);
        }
    }

    private static void testGetISO3CountryEmptyRegion() {
        Locale locale = new Locale("en", "");
        try {
            locale.getISO3Country();
            throw new AssertionError("Expected MissingResourceException for empty region");
        } catch (MissingResourceException e) {
            System.out.println("Empty region test: Caught expected exception.");
        }
    }

    private static void testGetISO3CountryInvalidRegion() {
        Locale locale = new Locale("en", "ZZZZZ");
        try {
            locale.getISO3Country();
            throw new AssertionError("Expected MissingResourceException for invalid region");
        } catch (MissingResourceException e) {
            System.out.println("Invalid region test: Caught expected exception.");
        }
    }
}
