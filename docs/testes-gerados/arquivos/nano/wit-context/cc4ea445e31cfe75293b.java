/*
 * @test
 * @summary Tests for java.util.Locale.getDisplayVariant(Locale)
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetDisplayVariantWithNullLocale();
        testGetDisplayVariantWithEmptyVariant();
        testGetDisplayVariantWithValidLocale();
    }

    private static void testGetDisplayVariantWithNullLocale() {
        Locale locale = null;
        try {
            // Expecting NullPointerException as per method contract
            Locale.getDisplayVariant(locale);
            System.out.println("testGetDisplayVariantWithNullLocale: FAILED (no exception)");
        } catch (NullPointerException e) {
            System.out.println("testGetDisplayVariantWithNullLocale: PASSED");
        } catch (Throwable t) {
            System.out.println("testGetDisplayVariantWithNullLocale: FAILED (unexpected exception)");
        }
    }

    private static void testGetDisplayVariantWithEmptyVariant() {
        Locale locale = new Locale("en", "US");
        // Force baseLocale variant to empty string
        // Since Locale constructor with language and country sets variant to ""
        String result = locale.getDisplayVariant(locale);
        if ("".equals(result)) {
            System.out.println("testGetDisplayVariantWithEmptyVariant: PASSED");
        } else {
            System.out.println("testGetDisplayVariantWithEmptyVariant: FAILED (unexpected result: " + result + ")");
        }
    }

    private static void testGetDisplayVariantWithValidLocale() {
        Locale locale = new Locale("en", "US", "POSIX");
        String result = locale.getDisplayVariant(locale);
        // Since the variant is "POSIX", expect a non-empty string or specific pattern
        if (result != null && !result.isEmpty()) {
            System.out.println("testGetDisplayVariantWithValidLocale: PASSED");
        } else {
            System.out.println("testGetDisplayVariantWithValidLocale: FAILED (unexpected result: " + result + ")");
        }
    }
}
