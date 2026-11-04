/*
 * @test
 * @summary Test for Properties.containsValue(Object)
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testContainsValueWithExistingValue();
        testContainsValueWithNonExistingValue();
        testContainsValueWithNull();
    }

    static void testContainsValueWithExistingValue() {
        Properties props = new Properties();
        String key = "key1";
        String value = "value1";
        props.put(key, value);
        boolean result = props.containsValue(value);
        if (!result) {
            throw new AssertionError("Expected true for existing value") ;
        }
    }

    static void testContainsValueWithNonExistingValue() {
        Properties props = new Properties();
        props.put("key2", "value2");
        boolean result = props.containsValue("nonexistent");
        if (result) {
            throw new AssertionError("Expected false for non-existing value") ;
        }
    }

    static void testContainsValueWithNull() {
        Properties props = new Properties();
        props.put("key3", null);
        boolean result = props.containsValue(null);
        if (!result) {
            throw new AssertionError("Expected true for null value") ;
        }
    }
}
