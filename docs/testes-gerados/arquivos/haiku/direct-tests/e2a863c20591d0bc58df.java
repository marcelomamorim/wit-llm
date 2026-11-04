/*
 * @test
 * @summary Verifies setProperty returns the previous value and stores the new value observable via getProperty and defaults.
 * @run main PropertiesWitupTest
 */

import java.util.Properties;
import java.util.Set;

public class PropertiesWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFirstInsertReturnsNull() {
        Properties p = new Properties();
        Object previous = p.setProperty("key", "value");
        check(previous == null, "first setProperty should return null, got " + previous);
        check("value".equals(p.getProperty("key")), "getProperty should return stored value");
    }

    static void testOverwriteReturnsPreviousValue() {
        Properties p = new Properties();
        p.setProperty("k", "old");
        Object previous = p.setProperty("k", "new");
        check("old".equals(previous), "setProperty should return previous value, got " + previous);
        check("new".equals(p.getProperty("k")), "getProperty should return new value");
    }

    static void testSameValueOverwrite() {
        Properties p = new Properties();
        p.setProperty("k", "v");
        Object previous = p.setProperty("k", "v");
        check("v".equals(previous), "setProperty with same value should return previous value");
        check("v".equals(p.getProperty("k")), "value should remain v");
    }

    static void testDefaultsNotAffected() {
        Properties defaults = new Properties();
        defaults.setProperty("d", "default");
        Properties p = new Properties(defaults);
        Object previous = p.setProperty("d", "local");
        check(previous == null, "previous value in own table should be null, got " + previous);
        check("local".equals(p.getProperty("d")), "local value should take precedence");
        check("default".equals(defaults.getProperty("d")), "defaults should be unchanged");
    }

    static void testFallbackToDefaultsWhenNotSet() {
        Properties defaults = new Properties();
        defaults.setProperty("only", "fromDefaults");
        Properties p = new Properties(defaults);
        check("fromDefaults".equals(p.getProperty("only")), "getProperty should fall back to defaults");
        p.setProperty("only", "set");
        check("set".equals(p.getProperty("only")), "set value should override defaults");
    }

    static void testKeyAppearsInNames() {
        Properties p = new Properties();
        p.setProperty("alpha", "1");
        p.setProperty("beta", "2");
        Set<String> names = p.stringPropertyNames();
        check(names.contains("alpha"), "stringPropertyNames should contain alpha");
        check(names.contains("beta"), "stringPropertyNames should contain beta");
        check(names.size() == 2, "expected 2 names, got " + names.size());
    }

    static void testEmptyStringValue() {
        Properties p = new Properties();
        Object previous = p.setProperty("empty", "");
        check(previous == null, "first insert of empty value should return null");
        check("".equals(p.getProperty("empty")), "empty string value should be stored");
    }

    public static void main(String[] args) {
        testFirstInsertReturnsNull();
        testOverwriteReturnsPreviousValue();
        testSameValueOverwrite();
        testDefaultsNotAffected();
        testFallbackToDefaultsWhenNotSet();
        testKeyAppearsInNames();
        testEmptyStringValue();
    }
}
