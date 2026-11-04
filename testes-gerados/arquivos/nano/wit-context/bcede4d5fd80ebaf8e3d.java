/*
 * @test
 * @summary Test getFallbackLocale method for null inputs
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testGetFallbackLocaleWithNullBaseName();
        testGetFallbackLocaleWithNullLocale();
        testGetFallbackLocaleWithValidInputs();
    }

    public static void testGetFallbackLocaleWithNullBaseName() {
        try {
            java.util.ResourceBundle.getFallbackLocale("", null);
            System.out.println("FAIL: Expected NullPointerException for null baseName");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null baseName");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null baseName: " + e);
        }
    }

    public static void testGetFallbackLocaleWithNullLocale() {
        try {
            java.util.ResourceBundle.getFallbackLocale(null, new Locale("en"));
            System.out.println("FAIL: Expected NullPointerException for null locale");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null locale");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null locale: " + e);
        }
    }

    public static void testGetFallbackLocaleWithValidInputs() {
        try {
            Locale result = java.util.ResourceBundle.getFallbackLocale("base", new Locale("en"));
            if (result == null) {
                System.out.println("FAIL: Expected non-null Locale for valid inputs");
            } else {
                System.out.println("PASS: getFallbackLocale returned: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for valid inputs: " + e);
        }
    }
}
