/*
 * @test
 * @summary Test java.util.Locale.LanguageRange(String) constructor for null input
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testLanguageRangeNullArgument();
        testLanguageRangeValidArgument();
    }

    private static void testLanguageRangeNullArgument() {
        try {
            new java.util.Locale.LanguageRange(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception, test passes
        }
    }

    private static void testLanguageRangeValidArgument() {
        // Test with a valid non-null language range string
        java.util.Locale.LanguageRange lr = new java.util.Locale.LanguageRange("en-US");
        if (lr == null) {
            throw new RuntimeException("LanguageRange constructor returned null");
        }
    }
}
