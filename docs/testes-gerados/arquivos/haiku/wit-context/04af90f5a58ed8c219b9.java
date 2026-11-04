/*
 * @test
 * @summary Witup-generated observable tests for Locale.getISO3Country()
 * @run main LocaleWitupTest
 */

import java.util.Locale;
import java.util.MissingResourceException;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testKnownCountryReturnsIso3();
        testAnotherKnownCountry();
        testUnknownRegionThrowsMissingResourceException();
        testLanguageOnlyLocaleDoesNotYieldIso3Code();
    }

    static void testKnownCountryReturnsIso3() {
        Locale l = new Locale("en", "US");
        String iso3 = l.getISO3Country();
        if (!"USA".equals(iso3)) {
            throw new RuntimeException("Expected USA but got: " + iso3);
        }
    }

    static void testAnotherKnownCountry() {
        Locale l = new Locale("ja", "JP");
        String iso3 = l.getISO3Country();
        if (!"JPN".equals(iso3)) {
            throw new RuntimeException("Expected JPN but got: " + iso3);
        }
    }

    static void testUnknownRegionThrowsMissingResourceException() {
        Locale l = new Locale("en", "ZZ");
        boolean thrown = false;
        try {
            l.getISO3Country();
        } catch (MissingResourceException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected MissingResourceException for unknown region");
        }
    }

    static void testLanguageOnlyLocaleDoesNotYieldIso3Code() {
        Locale l = new Locale("en");
        String iso3 = null;
        try {
            iso3 = l.getISO3Country();
        } catch (MissingResourceException e) {
            return;
        }
        if (iso3 != null && iso3.length() == 3) {
            throw new RuntimeException("Language-only locale must not produce a 3-letter country code: " + iso3);
        }
    }
}
