/* @test    @summary Test for Properties.remove(Object, Object) method behavior.    @run main PropertiesWitupTest */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testRemoveExistingKeyValue();
        testRemoveNonExistingKey();
        testRemoveWithNullKey();
        testRemoveWithNullValue();
        testRemoveNullKeyAndValue();
    }

    static void testRemoveExistingKeyValue() {
        Properties props = new Properties();
        String key = "testKey";
        String value = "testValue";
        props.setProperty(key, value);
        boolean removed = props.remove(key, value);
        if (!removed) {
            throw new AssertionError("Failed to remove existing key-value pair") ;
        }
        if (props.containsKey(key)) {
            throw new AssertionError("Key still present after removal") ;
        }
    }

    static void testRemoveNonExistingKey() {
        Properties props = new Properties();
        String key = "nonExistentKey";
        String value = "someValue";
        boolean removed = props.remove(key, value);
        if (removed) {
            throw new AssertionError("Removal returned true for non-existent key-value") ;
        }
    }

    static void testRemoveWithNullKey() {
        Properties props = new Properties();
        String value = "value";
        try {
            boolean result = props.remove(null, value);
            if (result) {
                throw new AssertionError("Expected false when removing null key") ;
            }
        } catch (NullPointerException e) {
            // Expected if implementation throws NPE
        }
    }

    static void testRemoveWithNullValue() {
        Properties props = new Properties();
        String key = "key";
        try {
            boolean result = props.remove(key, null);
            if (result) {
                throw new AssertionError("Expected false when removing with null value") ;
            }
        } catch (NullPointerException e) {
            // Expected if implementation throws NPE
        }
    }

    static void testRemoveNullKeyAndValue() {
        Properties props = new Properties();
        try {
            boolean result = props.remove(null, null);
            if (result) {
                throw new AssertionError("Expected false when removing null key and null value") ;
            }
        } catch (NullPointerException e) {
            // Expected if implementation throws NPE
        }
    }
}
