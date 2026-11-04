/*
 * @test
 * @summary Test Properties(Properties defaults) constructor behavior
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        // Test with null defaults
        Properties p1 = new Properties(null);
        if (p1 == null) throw new RuntimeException("Properties instance is null");
        if (p1.getDefaults() != null) throw new RuntimeException("Defaults should be null");

        // Test with non-null defaults
        Properties defaults = new Properties();
        defaults.setProperty("key1", "value1");
        Properties p2 = new Properties(defaults);
        if (p2 == null) throw new RuntimeException("Properties instance is null");
        if (p2.getDefaults() != defaults) throw new RuntimeException("Defaults not set correctly");

        // Check that property from defaults is accessible
        String val = p2.getProperty("key1");
        if (!"value1".equals(val)) throw new RuntimeException("Property from defaults not accessible");

        // Check that setting property in p2 does not affect defaults
        p2.setProperty("key2", "value2");
        if (defaults.getProperty("key2") != null) throw new RuntimeException("Defaults should not contain key2");

        System.out.println("Properties(Properties defaults) constructor tests passed.");
    }
}
