/*
 * @test
 * @summary Test java.util.Properties.replace(Object key, Object value) method
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Test replace on empty Properties - should return null
        Object result = props.replace("key1", "value1");
        if (result != null) {
            throw new RuntimeException("Expected null when replacing non-existing key");
        }

        // Put a key-value pair
        props.put("key1", "oldValue");

        // Replace existing key - should return old value
        Object oldValue = props.replace("key1", "newValue");
        if (!"oldValue".equals(oldValue)) {
            throw new RuntimeException("Expected old value 'oldValue' when replacing existing key");
        }

        // Verify that the value was updated
        Object currentValue = props.get("key1");
        if (!"newValue".equals(currentValue)) {
            throw new RuntimeException("Expected new value 'newValue' after replace");
        }

        // Replace with null value - should return old value
        Object oldValue2 = props.replace("key1", null);
        if (!"newValue".equals(oldValue2)) {
            throw new RuntimeException("Expected old value 'newValue' when replacing with null");
        }

        // Verify that the value is now null
        Object currentValue2 = props.get("key1");
        if (currentValue2 != null) {
            throw new RuntimeException("Expected null value after replacing with null");
        }

        // Replace with null key - allowed by Hashtable, should return null if key not present
        Object resultNullKey = props.replace(null, "someValue");
        if (resultNullKey != null) {
            throw new RuntimeException("Expected null when replacing null key not present");
        }

        // Put null key with a value
        props.put(null, "nullKeyValue");

        // Replace null key - should return old value
        Object oldNullKeyValue = props.replace(null, "newNullKeyValue");
        if (!"nullKeyValue".equals(oldNullKeyValue)) {
            throw new RuntimeException("Expected old value 'nullKeyValue' when replacing null key");
        }

        // Verify updated value for null key
        Object currentNullKeyValue = props.get(null);
        if (!"newNullKeyValue".equals(currentNullKeyValue)) {
            throw new RuntimeException("Expected new value 'newNullKeyValue' after replace for null key");
        }
    }
}
