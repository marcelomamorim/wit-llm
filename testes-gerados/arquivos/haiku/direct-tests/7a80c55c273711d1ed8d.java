/*
 * @test
 * @summary Verifies observable behavior of Properties(int initialCapacity), which delegates to this(null, initialCapacity)
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testZeroCapacityIsUsable();
        testSmallCapacitySetAndGet();
        testLargeCapacitySetAndGet();
        testNoDefaultsForMissingKey();
        testGetPropertyWithFallbackWhenMissing();
        testEmptyAfterConstruction();
        testOverwriteValue();
    }

    static void testZeroCapacityIsUsable() {
        Properties p = new Properties(0);
        check(p.size() == 0, "size should be 0 for new Properties(0)");
        p.setProperty("a", "1");
        check("1".equals(p.getProperty("a")), "value set on zero-capacity Properties must be retrievable");
        check(p.size() == 1, "size should be 1 after one setProperty");
    }

    static void testSmallCapacitySetAndGet() {
        Properties p = new Properties(4);
        p.setProperty("key1", "value1");
        p.setProperty("key2", "value2");
        check("value1".equals(p.getProperty("key1")), "key1 should map to value1");
        check("value2".equals(p.getProperty("key2")), "key2 should map to value2");
    }

    static void testLargeCapacitySetAndGet() {
        Properties p = new Properties(1 << 16);
        for (int i = 0; i < 100; i++) {
            p.setProperty("k" + i, "v" + i);
        }
        check(p.size() == 100, "size should be 100, was " + p.size());
        for (int i = 0; i < 100; i++) {
            check(("v" + i).equals(p.getProperty("k" + i)), "wrong value for k" + i);
        }
    }

    static void testNoDefaultsForMissingKey() {
        Properties p = new Properties(8);
        check(p.getProperty("missing") == null, "missing key must return null when no defaults exist");
    }

    static void testGetPropertyWithFallbackWhenMissing() {
        Properties p = new Properties(8);
        check("dflt".equals(p.getProperty("missing", "dflt")), "fallback value must be returned for missing key");
        p.setProperty("present", "x");
        check("x".equals(p.getProperty("present", "dflt")), "existing value must take precedence over fallback");
    }

    static void testEmptyAfterConstruction() {
        Properties p = new Properties(32);
        check(p.isEmpty(), "new Properties must be empty");
        check(p.stringPropertyNames().isEmpty(), "stringPropertyNames must be empty for new Properties");
    }

    static void testOverwriteValue() {
        Properties p = new Properties(2);
        p.setProperty("k", "old");
        p.setProperty("k", "new");
        check("new".equals(p.getProperty("k")), "setProperty must overwrite previous value");
        check(p.size() == 1, "overwrite must not increase size");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
