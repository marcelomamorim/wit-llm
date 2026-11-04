/*
 * @test
 * @summary Tests for Locale.getUnicodeLocaleType(String key) method.
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetUnicodeLocaleTypeWithEmptyKey();
        testGetUnicodeLocaleTypeWithInvalidKey();
        testGetUnicodeLocaleTypeWithValidKey();
    }

    private static void testGetUnicodeLocaleTypeWithEmptyKey() {
        Locale locale = Locale.US;
        try {
            String result = locale.getUnicodeLocaleType("");
            System.out.println("Empty key test: Unexpected success, result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Empty key test: Caught expected IllegalArgumentException");
        }
    }

    private static void testGetUnicodeLocaleTypeWithInvalidKey() {
        Locale locale = Locale.US;
        String invalidKey = "a"; // invalid because length != 2
        try {
            String result = locale.getUnicodeLocaleType(invalidKey);
            System.out.println("Invalid key test: Unexpected success, result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid key test: Caught expected IllegalArgumentException");
        }
    }

    private static void testGetUnicodeLocaleTypeWithValidKey() {
        Locale locale = Locale.US;
        String validKey = "nu"; // assuming 'nu' is a valid Unicode locale key
        try {
            String result = locale.getUnicodeLocaleType(validKey);
            System.out.println("Valid key test: result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Valid key test: Unexpected IllegalArgumentException");
        }
    }
}
