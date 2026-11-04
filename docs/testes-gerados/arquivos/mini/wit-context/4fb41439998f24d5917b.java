/*
 * @test
 * @summary Test java.lang.System.setProperties(Properties) for basic behavior and null argument handling
 * @run main SystemWitupTest
 */

import java.lang.System;
import java.util.Properties;

public class SystemWitupTest {
    public static void main(String[] args) {
        testSetPropertiesWithNonNull();
        testSetPropertiesWithNull();
    }

    private static void testSetPropertiesWithNonNull() {
        Properties p = new Properties();
        p.setProperty("key", "value");
        System.setProperties(p);
        Properties current = System.getProperties();
        if (current == null) {
            throw new RuntimeException("System.getProperties() returned null after setProperties with non-null");
        }
        String val = current.getProperty("key");
        if (!"value".equals(val)) {
            throw new RuntimeException("Property value mismatch after setProperties: expected 'value', got '" + val + "'");
        }
    }

    private static void testSetPropertiesWithNull() {
        // Passing null should reset to a new Properties instance (not null) with default init
        System.setProperties(null);
        Properties current = System.getProperties();
        if (current == null) {
            throw new RuntimeException("System.getProperties() returned null after setProperties with null");
        }
        // The properties should be empty or contain standard defaults, but at least not null
        // We test that it is a Properties instance and not null
        if (!(current instanceof Properties)) {
            throw new RuntimeException("System.getProperties() is not a Properties instance after setProperties with null");
        }
    }
}
