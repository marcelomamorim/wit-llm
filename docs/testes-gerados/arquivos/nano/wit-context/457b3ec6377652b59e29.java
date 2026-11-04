/*
 * @test
 * @summary Basic tests for Properties constructor with defaults
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testDefaultProperties();
        testPropertiesWithDefaults();
    }

    static void testDefaultProperties() {
        Properties props = new Properties();
        if (props.size() != 0) {
            throw new AssertionError("Expected empty Properties, but size was: " + props.size());
        }
        System.out.println("testDefaultProperties passed");
    }

    static void testPropertiesWithDefaults() {
        Properties defaults = new Properties();
        defaults.setProperty("key1", "value1");
        Properties props = new Properties(defaults);
        if (props.size() != 0) {
            throw new AssertionError("Expected empty Properties, but size was: " + props.size());
        }
        if (!props.getProperty("key1", "") .equals("value1")) {
            throw new AssertionError("Default property not inherited");
        }
        System.out.println("testPropertiesWithDefaults passed");
    }
}
