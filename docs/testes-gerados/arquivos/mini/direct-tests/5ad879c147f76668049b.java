/*
 * @test
 * @summary Test java.util.Properties.put(Object,Object) basic behavior
 * @run main java.util.PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties props = new Properties();

        // Test put with String key and String value
        Object prev = props.put("key1", "value1");
        if (prev != null) {
            throw new RuntimeException("Expected null previous value for new key");
        }
        Object val = props.get("key1");
        if (!"value1".equals(val)) {
            throw new RuntimeException("Expected value1 after put");
        }

        // Test put with existing key returns previous value
        prev = props.put("key1", "value2");
        if (!"value1".equals(prev)) {
            throw new RuntimeException("Expected previous value1 when overwriting key");
        }
        val = props.get("key1");
        if (!"value2".equals(val)) {
            throw new RuntimeException("Expected value2 after overwrite");
        }

        // Test put with non-String key and value
        Object keyObj = new Object();
        Object valueObj = new Object();
        prev = props.put(keyObj, valueObj);
        if (prev != null) {
            throw new RuntimeException("Expected null previous value for new Object key");
        }
        val = props.get(keyObj);
        if (val != valueObj) {
            throw new RuntimeException("Expected valueObj after put with Object key");
        }

        // Test put with null key and null value allowed by Hashtable
        // Hashtable allows null values but not null keys, so expect NullPointerException
        boolean npeThrown = false;
        try {
            props.put(null, "someValue");
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException when putting null key");
        }

        // Test put with null value allowed
        prev = props.put("keyNullValue", null);
        if (prev != null) {
            throw new RuntimeException("Expected null previous value for new key with null value");
        }
        val = props.get("keyNullValue");
        if (val != null) {
            throw new RuntimeException("Expected null value after put with null value");
        }
    }
}
