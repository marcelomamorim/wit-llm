/*
 * @test
 * @summary Tests System.getProperty(String, String) argument validation and default handling
 * @run main SystemWitupTest
 */
public class SystemWitupTest {
    private static final String MISSING_KEY = "witup.test.getProperty.missing.key";
    private static final String EXISTING_KEY = "witup.test.getProperty.existing.key";

    public static void main(String[] args) {
        testNullKeyThrowsNPE();
        testEmptyKeyThrowsIAE();
        testMissingKeyReturnsDefault();
        testMissingKeyNullDefaultReturnsNull();
        testExistingKeyIgnoresDefault();
    }

    static void testNullKeyThrowsNPE() {
        try {
            System.getProperty(null, "x");
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null key");
    }

    static void testEmptyKeyThrowsIAE() {
        try {
            System.getProperty("", "x");
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for empty key");
    }

    static void testMissingKeyReturnsDefault() {
        System.clearProperty(MISSING_KEY);
        String value = System.getProperty(MISSING_KEY, "d");
        if (!"d".equals(value)) {
            throw new RuntimeException("expected default value, got: " + value);
        }
    }

    static void testMissingKeyNullDefaultReturnsNull() {
        System.clearProperty(MISSING_KEY);
        String value = System.getProperty(MISSING_KEY, null);
        if (value != null) {
            throw new RuntimeException("expected null for missing key with null default, got: " + value);
        }
    }

    static void testExistingKeyIgnoresDefault() {
        System.setProperty(EXISTING_KEY, "v");
        try {
            String value = System.getProperty(EXISTING_KEY, "d");
            if (!"v".equals(value)) {
                throw new RuntimeException("expected existing value, got: " + value);
            }
        } finally {
            System.clearProperty(EXISTING_KEY);
        }
    }
}
