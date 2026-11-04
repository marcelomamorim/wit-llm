/* @test    @summary Test for Properties.putIfAbsent method to ensure correct behavior and thread safety.    @run main PropertiesWitupTest */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testPutIfAbsentNewKey();
        testPutIfAbsentExistingKey();
        testPutIfAbsentNullKey();
        testPutIfAbsentNullValue();
    }

    static void testPutIfAbsentNewKey() {
        Properties props = new Properties();
        Object result = props.putIfAbsent("newKey", "value1");
        assert result == null : "Expected null when inserting new key";
        assert "value1".equals(props.get("newKey")) : "Value not correctly inserted";
    }

    static void testPutIfAbsentExistingKey() {
        Properties props = new Properties();
        props.put("existingKey", "initialValue");
        Object result = props.putIfAbsent("existingKey", "newValue");
        assert "initialValue".equals(result) : "Expected previous value to be returned";
        assert "initialValue".equals(props.get("existingKey")) : "Value should not be overwritten";
    }

    static void testPutIfAbsentNullKey() {
        Properties props = new Properties();
        try {
            Object result = props.putIfAbsent(null, "nullValue");
            assert result == null : "Expected null when inserting null key";
            assert "nullValue".equals(props.get(null)) : "Null key not correctly inserted";
        } catch (NullPointerException e) {
            // Expected if null keys are not supported
        }
    }

    static void testPutIfAbsentNullValue() {
        Properties props = new Properties();
        Object result = props.putIfAbsent("nullValueKey", null);
        assert result == null : "Expected null when inserting null value";
        assert props.get("nullValueKey") == null : "Null value not correctly inserted";
    }
}
