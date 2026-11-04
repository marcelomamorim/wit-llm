/*
 * @test
 * @summary Test java.util.Properties(Properties defaults) constructor behavior
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        // Test constructor with null defaults
        Properties p1 = new Properties(null);
        if (p1 == null) {
            throw new RuntimeException("Constructor returned null");
        }

        // Test constructor with non-null defaults
        Properties defaults = new Properties();
        defaults.setProperty("key1", "value1");
        Properties p2 = new Properties(defaults);
        if (!"value1".equals(p2.getProperty("key1"))) {
            throw new RuntimeException("Defaults property not found in constructed Properties");
        }

        // Test that the defaults reference is preserved
        if (p2.defaults != defaults) {
            throw new RuntimeException("Defaults reference not preserved");
        }

        // Test that initial capacity 8 is used internally without throwing
        // (No direct access to internal map, so just ensure no exception)
        Properties p3 = new Properties(defaults);

        System.out.println("Properties(Properties) constructor tests passed.");
    }
}