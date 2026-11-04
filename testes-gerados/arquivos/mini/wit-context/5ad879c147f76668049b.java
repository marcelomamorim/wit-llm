/*
 * @test
 * @summary Test java.util.Properties.put(Object key, Object value) for null key or value
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Test normal put with non-null key and value
        Object prev = props.put("key1", "value1");
        if (prev != null) {
            throw new RuntimeException("Expected null previous value for new key");
        }

        // Test put with existing key returns previous value
        prev = props.put("key1", "value2");
        if (!"value1".equals(prev)) {
            throw new RuntimeException("Expected previous value 'value1' for key1");
        }

        // Test put with null key - expect NullPointerException
        try {
            props.put(null, "value");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }

        // Test put with null value - expect NullPointerException
        try {
            props.put("key2", null);
            throw new RuntimeException("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
        }

        // Test put with both key and value non-null again
        prev = props.put("key3", "value3");
        if (prev != null) {
            throw new RuntimeException("Expected null previous value for new key3");
        }

        System.out.println("Properties.put tests passed.");
    }
}
