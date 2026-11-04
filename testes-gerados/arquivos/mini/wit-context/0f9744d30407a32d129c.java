/*
 * @test
 * @summary Test java.lang.System.clearProperty(String) for key validation and permission check
 * @run main SystemWitupTest
 */

public class SystemWitupTest {
    public static void main(String[] args) {
        testClearPropertyNullKey();
        testClearPropertyEmptyKey();
        testClearPropertyNormalKey();
    }

    private static void testClearPropertyNullKey() {
        try {
            System.clearProperty(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testClearPropertyEmptyKey() {
        try {
            System.clearProperty("");
            throw new RuntimeException("Expected IllegalArgumentException for empty key");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testClearPropertyNormalKey() {
        String key = "test.key.for.clearProperty";
        String value = "testValue";
        System.setProperty(key, value);
        String removed = System.clearProperty(key);
        if (!value.equals(removed)) {
            throw new RuntimeException("clearProperty did not return the expected value");
        }
        String afterClear = System.getProperty(key);
        if (afterClear != null) {
            throw new RuntimeException("Property was not cleared properly");
        }
    }
}
