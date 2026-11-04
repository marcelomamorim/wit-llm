/*
 * @test
 * @summary Test java.util.Properties.replace(Object key, Object value) for null key or value and normal replacement
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        testReplaceWithNullKey();
        testReplaceWithNullValue();
        testReplaceNormal();
    }

    private static void testReplaceWithNullKey() {
        Properties props = new Properties();
        props.setProperty("key1", "value1");
        try {
            props.replace(null, "value2");
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceWithNullValue() {
        Properties props = new Properties();
        props.setProperty("key1", "value1");
        try {
            props.replace("key1", null);
            throw new RuntimeException("Expected NullPointerException for null value");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceNormal() {
        Properties props = new Properties();
        props.setProperty("key1", "value1");
        Object oldValue = props.replace("key1", "value2");
        if (!"value1".equals(oldValue)) {
            throw new RuntimeException("replace should return old value");
        }
        Object newValue = props.getProperty("key1");
        if (!"value2".equals(newValue)) {
            throw new RuntimeException("replace should update the value");
        }
        // Replace non-existing key returns null
        Object nullOld = props.replace("key2", "value3");
        if (nullOld != null) {
            throw new RuntimeException("replace on non-existing key should return null");
        }
    }
}
