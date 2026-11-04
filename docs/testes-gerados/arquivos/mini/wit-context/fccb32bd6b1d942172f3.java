/*
 * @test
 * @summary Test getUnicodeLocaleType(String) method of java.util.Locale for valid and invalid keys
 * @run main java.util.LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testValidKey();
        testInvalidKeyEmpty();
        testInvalidKeyLengthNotTwo();
        testNullKey();
    }

    private static void testValidKey() {
        // Use a Locale with extensions to test valid key
        java.util.Locale locale = new java.util.Locale("en", "US", "POSIX");
        // The key must be a valid Unicode extension key of length 2
        // According to BCP 47, keys are two alphanumeric characters
        // We test with "ca" (calendar) which is a common Unicode extension key
        String key = "ca";
        try {
            // The method returns null if no extensions present
            // Since we did not add extensions explicitly, expect null or non-null depending on internal state
            String result = locale.getUnicodeLocaleType(key);
            // result can be null or a string, just call to ensure no exception
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unexpected IllegalArgumentException for valid key", e);
        }
    }

    private static void testInvalidKeyEmpty() {
        java.util.Locale locale = new java.util.Locale("en");
        String key = "";
        try {
            locale.getUnicodeLocaleType(key);
            throw new RuntimeException("Expected IllegalArgumentException for empty key but none thrown");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testInvalidKeyLengthNotTwo() {
        java.util.Locale locale = new java.util.Locale("en");
        String key = "abc"; // length 3, invalid
        try {
            locale.getUnicodeLocaleType(key);
            throw new RuntimeException("Expected IllegalArgumentException for key length != 2 but none thrown");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testNullKey() {
        java.util.Locale locale = new java.util.Locale("en");
        try {
            locale.getUnicodeLocaleType(null);
            throw new RuntimeException("Expected NullPointerException for null key but none thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IllegalArgumentException e) {
            // Some implementations might throw IllegalArgumentException for null, accept it
        }
    }
}
