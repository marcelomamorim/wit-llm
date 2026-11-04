/*
 * @test
 * @summary Test java.util.Properties.replace(Object key, Object oldValue, Object newValue)
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Test replace on empty Properties - should return false
        boolean replaced = props.replace("key", "old", "new");
        if (replaced) {
            throw new RuntimeException("replace returned true on empty Properties");
        }

        // Put a key-value pair
        props.put("key", "value");

        // Replace with wrong oldValue - should return false and not replace
        replaced = props.replace("key", "wrongValue", "newValue");
        if (replaced) {
            throw new RuntimeException("replace returned true when oldValue did not match");
        }
        Object val = props.get("key");
        if (!"value".equals(val)) {
            throw new RuntimeException("Value changed incorrectly when oldValue did not match");
        }

        // Replace with correct oldValue - should return true and replace
        replaced = props.replace("key", "value", "newValue");
        if (!replaced) {
            throw new RuntimeException("replace returned false when oldValue matched");
        }
        val = props.get("key");
        if (!"newValue".equals(val)) {
            throw new RuntimeException("Value not replaced correctly");
        }

        // Replace with null key and values
        props.put(null, null);
        replaced = props.replace(null, null, "notNull");
        if (!replaced) {
            throw new RuntimeException("replace returned false when replacing null key and null oldValue");
        }
        val = props.get(null);
        if (!"notNull".equals(val)) {
            throw new RuntimeException("Value for null key not replaced correctly");
        }

        // Replace with null newValue
        replaced = props.replace(null, "notNull", null);
        if (!replaced) {
            throw new RuntimeException("replace returned false when replacing with null newValue");
        }
        val = props.get(null);
        if (val != null) {
            throw new RuntimeException("Value for null key not replaced with null correctly");
        }

        System.out.println("Properties.replace tests passed");
    }
}
