/*
 * @test
 * @summary Test getDisplayName(Locale) method of IsoFields for various locales.
 * @run main IsoFieldsWitupTest
 */

import java.time.temporal.IsoFields;
import java.util.Locale;

public class IsoFieldsWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameDefaultLocale();
        testGetDisplayNameUSLocale();
        testGetDisplayNameFrenchLocale();
        testGetDisplayNameNullLocale();
    }

    static void testGetDisplayNameDefaultLocale() {
        try {
            String result = IsoFields.getDisplayName(Locale.getDefault());
            System.out.println("Default locale display name: " + result);
        } catch (Exception e) {
            throw new RuntimeException("Failed testGetDisplayNameDefaultLocale", e);
        }
    }

    static void testGetDisplayNameUSLocale() {
        try {
            String result = IsoFields.getDisplayName(Locale.US);
            System.out.println("US locale display name: " + result);
        } catch (Exception e) {
            throw new RuntimeException("Failed testGetDisplayNameUSLocale", e);
        }
    }

    static void testGetDisplayNameFrenchLocale() {
        try {
            String result = IsoFields.getDisplayName(Locale.FRENCH);
            System.out.println("French locale display name: " + result);
        } catch (Exception e) {
            throw new RuntimeException("Failed testGetDisplayNameFrenchLocale", e);
        }
    }

    static void testGetDisplayNameNullLocale() {
        try {
            IsoFields.getDisplayName(null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null locale", e);
        }
    }
}
