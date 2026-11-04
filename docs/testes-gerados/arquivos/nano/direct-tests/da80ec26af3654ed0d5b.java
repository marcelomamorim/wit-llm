/*
 * @test
 * @summary Test the contains method of Properties class.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        testContainsWithExistingValue();
        testContainsWithNonExistingValue();
        testContainsWithNull();
    }

    static void testContainsWithExistingValue() {
        Properties props = new Properties();
        String key = "testKey";
        String value = "testValue";
        props.put(key, value);
        boolean result = props.contains(value);
        if (!result) {
            throw new AssertionError("Expected properties to contain value: " + value);
        }
    }

    static void testContainsWithNonExistingValue() {
        Properties props = new Properties();
        props.put("key1", "value1");
        boolean result = props.contains("nonExistingValue");
        if (result) {
            throw new AssertionError("Properties should not contain non-existing value.");
        }
    }

    static void testContainsWithNull() {
        Properties props = new Properties();
        props.put("key", "value");
        boolean result = false;
        try {
            result = props.contains(null);
        } catch (NullPointerException e) {
            // Expected if null is not supported
            return;
        }
        if (result) {
            throw new AssertionError("Properties should not contain null value.");
        }
    }
}
