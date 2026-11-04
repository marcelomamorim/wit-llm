/*
 * @test
 * @summary Test ResourceBundle.getFallbackLocale for null inputs and normal behavior
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testGetFallbackLocaleWithNulls();
        testGetFallbackLocaleWithValidInputs();
    }

    static void testGetFallbackLocaleWithNulls() {
        try {
            ResourceBundle.getFallbackLocale("baseName", null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
        try {
            ResourceBundle.getFallbackLocale(null, Locale.US);
            throw new AssertionError("Expected NullPointerException for null baseName");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGetFallbackLocaleWithValidInputs() {
        Locale result = ResourceBundle.getFallbackLocale("baseName", Locale.US);
        if (result != null) {
            throw new AssertionError("Expected null fallback locale, but got: " + result);
        }
    }
}
