/*
 * @test
 * @summary Test java.util.Properties.putIfAbsent behavior including null key/value handling
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Test putIfAbsent with valid key and value
        Object prev = props.putIfAbsent("key1", "value1");
        if (prev != null) {
            throw new AssertionError("Expected null previous value on first putIfAbsent");
        }
        Object val = props.getProperty("key1");
        if (!"value1".equals(val)) {
            throw new AssertionError("Expected value1 for key1, got: " + val);
        }

        // Test putIfAbsent with existing key should not overwrite
        prev = props.putIfAbsent("key1", "value2");
        if (!"value1".equals(prev)) {
            throw new AssertionError("Expected previous value1 on putIfAbsent with existing key");
        }
        val = props.getProperty("key1");
        if (!"value1".equals(val)) {
            throw new AssertionError("Expected value1 for key1 after putIfAbsent with existing key, got: " + val);
        }

        // Test putIfAbsent with null key throws NullPointerException
        try {
            props.putIfAbsent(null, "value");
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }

        // Test putIfAbsent with null value throws NullPointerException
        try {
            props.putIfAbsent("key2", null);
            throw new AssertionError("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
        }
    }
}
