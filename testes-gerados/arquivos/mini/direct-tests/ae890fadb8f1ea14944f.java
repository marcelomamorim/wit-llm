/*
 * @test
 * @summary Test java.util.Properties remove(Object key, Object value) method
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Test remove returns false when key-value pair not present
        boolean removed = props.remove("key1", "value1");
        if (removed) {
            throw new RuntimeException("remove returned true for non-existing key-value pair");
        }

        // Add a key-value pair and test remove returns true
        props.setProperty("key1", "value1");
        removed = props.remove("key1", "value1");
        if (!removed) {
            throw new RuntimeException("remove returned false for existing key-value pair");
        }

        // After removal, key should no longer be present
        if (props.containsKey("key1")) {
            throw new RuntimeException("key1 still present after removal");
        }

        // Add key with different value and test remove with wrong value returns false
        props.setProperty("key2", "value2");
        removed = props.remove("key2", "wrongValue");
        if (removed) {
            throw new RuntimeException("remove returned true for key with different value");
        }

        // Remove with correct value returns true
        removed = props.remove("key2", "value2");
        if (!removed) {
            throw new RuntimeException("remove returned false for correct key-value pair");
        }

        // Remove with null key and value (should return false)
        removed = props.remove(null, null);
        if (removed) {
            throw new RuntimeException("remove returned true for null key and value when none present");
        }

        // Add null key and null value explicitly (Properties extends Hashtable, which allows null keys and values)
        props.put(null, null);
        removed = props.remove(null, null);
        if (!removed) {
            throw new RuntimeException("remove returned false for null key and value when present");
        }

        System.out.println("All tests passed.");
    }
}
