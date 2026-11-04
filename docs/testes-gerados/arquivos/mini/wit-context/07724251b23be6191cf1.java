/*
 * @test
 * @summary Test Charset.isSupported(String) method behavior
 * @run main CharsetWitupTest
 */

public class CharsetWitupTest {
    public static void main(String[] args) {
        testIsSupportedWithValidCharset();
        testIsSupportedWithInvalidCharset();
        testIsSupportedWithNull();
    }

    private static void testIsSupportedWithValidCharset() {
        // "UTF-8" is a standard charset and should be supported
        boolean supported = java.nio.charset.Charset.isSupported("UTF-8");
        if (!supported) {
            throw new AssertionError("Expected UTF-8 to be supported");
        }
    }

    private static void testIsSupportedWithInvalidCharset() {
        // A likely invalid charset name should return false, not throw
        boolean supported = java.nio.charset.Charset.isSupported("NoSuchCharset1234");
        if (supported) {
            throw new AssertionError("Expected NoSuchCharset1234 to NOT be supported");
        }
    }

    private static void testIsSupportedWithNull() {
        // According to source, passing null should throw IllegalArgumentException
        try {
            java.nio.charset.Charset.isSupported(null);
            throw new AssertionError("Expected IllegalArgumentException for null charsetName");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Expected IllegalArgumentException but got " + e);
        }
    }
}
