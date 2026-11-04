/*
 * @test
 * @summary Verifies Properties.remove(Object key, Object value) conditional removal behavior
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveMatchingPair() {
        Properties p = new Properties();
        p.setProperty("a", "1");
        check(p.remove("a", "1"), "remove with matching key and value should return true");
        check(p.getProperty("a") == null, "entry should be removed after matching remove");
    }

    static void testRemoveNonMatchingValue() {
        Properties p = new Properties();
        p.setProperty("a", "1");
        check(!p.remove("a", "2"), "remove with non-matching value should return false");
        check("1".equals(p.getProperty("a")), "entry should remain unchanged when value does not match");
    }

    static void testRemoveMissingKey() {
        Properties p = new Properties();
        p.setProperty("a", "1");
        check(!p.remove("missing", "x"), "remove of absent key should return false");
        check("1".equals(p.getProperty("a")), "existing entry should be unaffected");
    }

    static void testRemoveIgnoresDefaults() {
        Properties defaults = new Properties();
        defaults.setProperty("a", "d");
        Properties p = new Properties(defaults);
        check(!p.remove("a", "d"), "remove should not consult defaults and should return false");
        check("d".equals(p.getProperty("a")), "default value should still be visible via getProperty");
    }

    static void testRemoveNonStringKeyAndValue() {
        Properties p = new Properties();
        p.put(Integer.valueOf(1), "x");
        check(p.remove(Integer.valueOf(1), "x"), "remove should match non-String key and value");
        check(p.isEmpty(), "map should be empty after removing the only entry");
    }

    public static void main(String[] args) {
        testRemoveMatchingPair();
        testRemoveNonMatchingValue();
        testRemoveMissingKey();
        testRemoveIgnoresDefaults();
        testRemoveNonStringKeyAndValue();
    }
}
