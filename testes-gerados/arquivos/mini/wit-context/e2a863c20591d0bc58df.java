/*
 * @test
 * @summary Test java.util.Properties.setProperty(String, String) for normal and null value cases
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Test normal usage: setProperty returns previous value or null if none
        Object prev = props.setProperty("key1", "value1");
        if (prev != null) {
            throw new RuntimeException("Expected null previous value for new key");
        }
        Object prev2 = props.setProperty("key1", "value2");
        if (!"value1".equals(prev2)) {
            throw new RuntimeException("Expected previous value 'value1' when overwriting key");
        }

        // Test that setProperty throws NullPointerException if value is null
        try {
            props.setProperty("key2", null);
            throw new RuntimeException("Expected NullPointerException when value is null");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test that setProperty accepts null key (Hashtable allows null keys? Actually Hashtable does not allow null keys, so expect NullPointerException)
        try {
            props.setProperty(null, "value");
            throw new RuntimeException("Expected NullPointerException when key is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
