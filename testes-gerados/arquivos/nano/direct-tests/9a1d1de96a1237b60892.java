/* @test    @summary Test the replace method of Properties for correct replacement behavior    @run main PropertiesWitupTest */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testReplaceExistingKey();
        testReplaceNonExistingKey();
        testReplaceNullKey();
        testReplaceNullValue();
    }

    private static void testReplaceExistingKey() {
        Properties props = new Properties();
        String key = "existingKey";
        String initialValue = "initial";
        String newValue = "replaced";
        props.setProperty(key, initialValue);
        Object result = props.replace(key, newValue);
        assert result != null : "Expected previous value, got null";
        assert result.equals(initialValue) : "Previous value mismatch";
        assert props.getProperty(key).equals(newValue) : "Value not replaced correctly";
    }

    private static void testReplaceNonExistingKey() {
        Properties props = new Properties();
        Object result = props.replace("nonExistingKey", "value");
        assert result == null : "Expected null for non-existing key";
        assert !props.containsKey("nonExistingKey") : "Key should not be present";
    }

    private static void testReplaceNullKey() {
        Properties props = new Properties();
        try {
            Object result = props.replace(null, "value");
            assert result == null : "Replacing null key should return null";
            assert !props.containsKey(null) : "Null key should not be added";
        } catch (NullPointerException e) {
            // Expected behavior if null keys are not allowed
        }
    }

    private static void testReplaceNullValue() {
        Properties props = new Properties();
        String key = "keyWithNullValue";
        props.setProperty(key, "initial");
        Object result = props.replace(key, null);
        assert result != null : "Expected previous value, got null";
        assert result.equals("initial") : "Previous value mismatch";
        assert props.getProperty(key) == null : "Value should be replaced with null";
    }
}
