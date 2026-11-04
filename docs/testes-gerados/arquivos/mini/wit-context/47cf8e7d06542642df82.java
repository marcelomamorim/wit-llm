/*
 * @test
 * @summary Test java.lang.System.setProperty(String, String) for expected exceptions and normal behavior
 * @run main SystemWitupTest
 */

public class SystemWitupTest {
    public static void main(String[] args) {
        testSetPropertyNullKey();
        testSetPropertyEmptyKey();
        testSetPropertyNormal();
    }

    private static void testSetPropertyNullKey() {
        try {
            System.setProperty(null, "value");
            throw new Error("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testSetPropertyEmptyKey() {
        try {
            System.setProperty("", "value");
            throw new Error("Expected IllegalArgumentException for empty key");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testSetPropertyNormal() {
        String key = "test.key";
        String value = "test.value";
        String previous = System.setProperty(key, value);
        if (previous != null) {
            // The property was previously set, ensure it was replaced
            if (previous.equals(value)) {
                throw new Error("Previous value should not be equal to new value");
            }
        }
        String current = System.getProperty(key);
        if (!value.equals(current)) {
            throw new Error("Property value was not set correctly");
        }
    }
}
