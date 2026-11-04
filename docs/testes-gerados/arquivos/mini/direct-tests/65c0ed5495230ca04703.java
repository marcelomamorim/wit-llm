/*
 * @test
 * @summary Test java.util.Properties.putIfAbsent behavior
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Test putIfAbsent returns null when key is absent
        Object ret1 = props.putIfAbsent("key1", "value1");
        if (ret1 != null) {
            throw new RuntimeException("Expected null return when key is absent");
        }

        // Test putIfAbsent returns existing value when key is present
        Object ret2 = props.putIfAbsent("key1", "value2");
        if (!"value1".equals(ret2)) {
            throw new RuntimeException("Expected existing value to be returned when key is present");
        }

        // Test that value was not replaced
        Object val = props.get("key1");
        if (!"value1".equals(val)) {
            throw new RuntimeException("Expected original value to remain after putIfAbsent");
        }

        // Test putIfAbsent with null key and value (Properties inherits Hashtable which allows null values but not null keys)
        try {
            props.putIfAbsent(null, "someValue");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
        }

        // Test putIfAbsent with null value
        Object ret3 = props.putIfAbsent("key2", null);
        if (ret3 != null) {
            throw new RuntimeException("Expected null return when key2 is absent");
        }
        Object val2 = props.get("key2");
        if (val2 != null) {
            throw new RuntimeException("Expected null value stored for key2");
        }

        System.out.println("Properties.putIfAbsent tests passed.");
    }
}
