/* @test    @summary Test Properties constructor with defaults argument    @run main PropertiesWitupTest */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testConstructorWithDefaults();
    }

    public static void testConstructorWithDefaults() {
        Properties defaultsProps = new Properties();
        defaultsProps.setProperty("key1", "value1");
        Properties props = new Properties(defaultsProps);
        String value = props.getProperty("key1");
        if (!"value1".equals(value)) {
            throw new AssertionError("Expected 'value1', but got: " + value);
        }
        String missingValue = props.getProperty("missingKey");
        if (missingValue != null) {
            throw new AssertionError("Expected null for missing key, but got: " + missingValue);
        }
    }
}
