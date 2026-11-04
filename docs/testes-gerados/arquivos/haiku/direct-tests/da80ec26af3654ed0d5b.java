/*
 * @test
 * @summary Verifies Properties.contains(Object) checks only the values stored in this Properties object
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testContainsStringValue();
        testContainsNonStringValue();
        testNotContainsMissingValue();
        testEmptyPropertiesContainsNothing();
        testContainsIgnoresDefaults();
        testContainsAfterRemove();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testContainsStringValue() {
        Properties p = new Properties();
        p.setProperty("a", "1");
        p.setProperty("b", "2");
        check(p.contains("1"), "expected contains(\"1\") to be true");
        check(p.contains("2"), "expected contains(\"2\") to be true");
        check(p.contains(new String("1")), "expected contains to use equals semantics");
    }

    static void testContainsNonStringValue() {
        Properties p = new Properties();
        p.put("k", Integer.valueOf(5));
        check(p.contains(Integer.valueOf(5)), "expected contains(Integer 5) to be true");
        check(!p.contains("5"), "expected contains(\"5\") to be false for Integer value");
    }

    static void testNotContainsMissingValue() {
        Properties p = new Properties();
        p.setProperty("a", "1");
        check(!p.contains("3"), "expected contains(\"3\") to be false");
        check(!p.contains("a"), "expected key not to be matched as a value");
    }

    static void testEmptyPropertiesContainsNothing() {
        Properties p = new Properties();
        check(!p.contains("x"), "expected empty Properties to contain no values");
        check(!p.contains(Integer.valueOf(0)), "expected empty Properties to contain no values");
    }

    static void testContainsIgnoresDefaults() {
        Properties defaults = new Properties();
        defaults.setProperty("d", "default-value");
        Properties p = new Properties(defaults);
        check(!p.contains("default-value"), "expected contains not to search defaults");
        check(p.getProperty("d").equals("default-value"), "expected getProperty to search defaults");
    }

    static void testContainsAfterRemove() {
        Properties p = new Properties();
        p.setProperty("a", "1");
        check(p.contains("1"), "expected value present before remove");
        p.remove("a");
        check(!p.contains("1"), "expected value absent after remove");
    }
}
