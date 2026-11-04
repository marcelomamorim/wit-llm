/*
 * @test
 * @summary Verifies that Properties(Properties defaults) uses the given defaults for lookups and keeps them live
 * @run main PropertiesWitupTest
 */

import java.util.Properties;
import java.util.Set;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testDefaultsAreUsedForMissingKeys();
        testOwnValueOverridesDefault();
        testDefaultsAreLiveReference();
        testNullDefaultsAllowed();
        testSizeCountsOnlyOwnEntries();
        testStringPropertyNamesIncludesDefaults();
        testChainedDefaults();
    }

    static void testDefaultsAreUsedForMissingKeys() {
        Properties defaults = new Properties();
        defaults.setProperty("color", "blue");
        Properties p = new Properties(defaults);
        check("blue".equals(p.getProperty("color")), "default value must be returned");
        check(p.getProperty("missing") == null, "missing key must return null");
        check("x".equals(p.getProperty("missing", "x")), "explicit fallback must be returned");
    }

    static void testOwnValueOverridesDefault() {
        Properties defaults = new Properties();
        defaults.setProperty("color", "blue");
        Properties p = new Properties(defaults);
        p.setProperty("color", "red");
        check("red".equals(p.getProperty("color")), "own value must override default");
        check("blue".equals(defaults.getProperty("color")), "defaults must not be modified");
    }

    static void testDefaultsAreLiveReference() {
        Properties defaults = new Properties();
        Properties p = new Properties(defaults);
        check(p.getProperty("late") == null, "key absent at construction must be null");
        defaults.setProperty("late", "v");
        check("v".equals(p.getProperty("late")), "later default update must be visible");
    }

    static void testNullDefaultsAllowed() {
        Properties q = new Properties((Properties) null);
        check(q.getProperty("x") == null, "null defaults must yield null for missing key");
        check("d".equals(q.getProperty("x", "d")), "fallback must still apply with null defaults");
    }

    static void testSizeCountsOnlyOwnEntries() {
        Properties defaults = new Properties();
        defaults.setProperty("a", "1");
        defaults.setProperty("b", "2");
        Properties p = new Properties(defaults);
        check(p.size() == 0, "size must count only own entries");
        p.setProperty("c", "3");
        check(p.size() == 1, "size must be 1 after one own entry");
    }

    static void testStringPropertyNamesIncludesDefaults() {
        Properties defaults = new Properties();
        defaults.setProperty("color", "blue");
        defaults.setProperty("size", "10");
        Properties p = new Properties(defaults);
        p.setProperty("color", "red");
        Set<String> names = p.stringPropertyNames();
        check(names.size() == 2, "names must include defaults without duplicates");
        check(names.contains("color"), "names must contain overridden key");
        check(names.contains("size"), "names must contain default-only key");
    }

    static void testChainedDefaults() {
        Properties base = new Properties();
        base.setProperty("size", "10");
        Properties middle = new Properties(base);
        Properties top = new Properties(middle);
        check("10".equals(top.getProperty("size")), "lookup must traverse chained defaults");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
