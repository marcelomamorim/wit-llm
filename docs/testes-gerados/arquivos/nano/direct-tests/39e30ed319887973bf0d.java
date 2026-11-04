/*
 * @test
 * @summary Test getCandidateLocales method of ResourceBundle for various locales
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testGetCandidateLocalesWithDefaultLocale();
        testGetCandidateLocalesWithSpecificLocale();
        testGetCandidateLocalesWithNullBaseName();
    }

    static void testGetCandidateLocalesWithDefaultLocale() {
        try {
            String baseName = "dummyBase";
            Locale locale = Locale.getDefault();
            List<Locale> locales = ResourceBundle.getCandidateLocales(baseName, locale);
            if (locales == null || locales.isEmpty()) {
                throw new AssertionError("Expected non-empty list for default locale");
            }
            System.out.println("testGetCandidateLocalesWithDefaultLocale passed.");
        } catch (Exception e) {
            throw new AssertionError("Exception in testGetCandidateLocalesWithDefaultLocale", e);
        }
    }

    static void testGetCandidateLocalesWithSpecificLocale() {
        try {
            String baseName = "dummyBase";
            Locale locale = new Locale("en", "US");
            List<Locale> locales = ResourceBundle.getCandidateLocales(baseName, locale);
            if (locales == null || locales.isEmpty()) {
                throw new AssertionError("Expected non-empty list for en_US locale");
            }
            // Check that the first locale is the most specific
            if (!locales.get(0).equals(locale)) {
                throw new AssertionError("First locale should be the specific locale en_US");
            }
            System.out.println("testGetCandidateLocalesWithSpecificLocale passed.");
        } catch (Exception e) {
            throw new AssertionError("Exception in testGetCandidateLocalesWithSpecificLocale", e);
        }
    }

    static void testGetCandidateLocalesWithNullBaseName() {
        try {
            Locale locale = Locale.getDefault();
            ResourceBundle.getCandidateLocales(null, locale);
            throw new AssertionError("Expected NullPointerException for null baseName");
        } catch (NullPointerException e) {
            System.out.println("testGetCandidateLocalesWithNullBaseName passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetCandidateLocalesWithNullBaseName", e);
        }
    }
}
