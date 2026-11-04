/* @test    @summary Test for Properties.setProperty method to ensure it correctly sets a property and returns previous value.    @run main PropertiesWitupTest */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testSetPropertyNewKey();
        testSetPropertyExistingKey();
    }

    private static void testSetPropertyNewKey() {
        Properties props = new Properties();
        Object result = props.setProperty("newKey", "newValue");
        assert result == null : "Expected null when setting a new property";
        Object value = props.get("newKey");
        assert "newValue".equals(value) : "Property value mismatch";
    }

    private static void testSetPropertyExistingKey() {
        Properties props = new Properties();
        props.setProperty("existingKey", "initialValue");
        Object previous = props.setProperty("existingKey", "newValue");
        assert "initialValue".equals(previous) : "Previous value should be 'initialValue'";
        Object value = props.get("existingKey");
        assert "newValue".equals(value) : "Updated property value mismatch";
    }
}
