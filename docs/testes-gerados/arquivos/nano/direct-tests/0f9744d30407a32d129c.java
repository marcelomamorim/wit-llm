/*
 * @test
 * @summary Test System.clearProperty for existing, non-existing, and null keys.
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.util.Properties;

public class SystemWitupTest {

    public static void main(String[] args) {
        testClearPropertyExistingKey();
        testClearPropertyNonExistingKey();
        testClearPropertyNullKey();
    }

    private static void testClearPropertyExistingKey() {
        String key = "testKey";
        String value = "testValue";
        Properties props = System.getProperties();
        props.setProperty(key, value);
        String removed = System.clearProperty(key);
        assert removed != null && removed.equals(value) : "Failed to remove existing property";
        assert System.getProperty(key) == null : "Property still exists after removal";
    }

    private static void testClearPropertyNonExistingKey() {
        String key = "nonExistingKey";
        String result = System.clearProperty(key);
        assert result == null : "Expected null when removing non-existing property";
        assert System.getProperty(key) == null : "Non-existing property should not exist";
    }

    private static void testClearPropertyNullKey() {
        try {
            System.clearProperty(null);
            assert false : "Expected NullPointerException for null key";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
