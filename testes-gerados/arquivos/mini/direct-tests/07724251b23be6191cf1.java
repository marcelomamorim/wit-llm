/*
 * @test
 * @summary Test Charset.isSupported(String) method
 * @run main CharsetWitupTest
 */

public class CharsetWitupTest {
    public static void main(String[] args) {
        // Test with a known charset name - should be supported
        if (!java.nio.charset.Charset.isSupported("UTF-8")) {
            throw new RuntimeException("UTF-8 charset should be supported");
        }

        // Test with a known charset name in different case - should be supported
        if (!java.nio.charset.Charset.isSupported("utf-8")) {
            throw new RuntimeException("utf-8 charset should be supported (case insensitive)");
        }

        // Test with a known alias - should be supported
        if (!java.nio.charset.Charset.isSupported("US-ASCII")) {
            throw new RuntimeException("US-ASCII charset should be supported");
        }

        // Test with a non-existent charset name - should not be supported
        if (java.nio.charset.Charset.isSupported("NON_EXISTENT_CHARSET_12345")) {
            throw new RuntimeException("NON_EXISTENT_CHARSET_12345 should not be supported");
        }

        // Test with null argument - expected to throw NullPointerException
        try {
            java.nio.charset.Charset.isSupported(null);
            throw new RuntimeException("Expected NullPointerException for null charsetName");
        } catch (NullPointerException npe) {
            // Expected
        }

        // Test with empty string - likely not supported
        if (java.nio.charset.Charset.isSupported("") ) {
            throw new RuntimeException("Empty string charset name should not be supported");
        }

        System.out.println("Charset.isSupported tests passed.");
    }
}
