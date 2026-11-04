/*
 * @test
 * @summary Behavioral tests for Properties.contains(Object value), including the null-value NullPointerException path
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testContainsExistingStringValue();
        testContainsMissingValue();
        testContainsNullThrowsNPE();
        testContainsNonStringValue();
        testContainsIgnoresDefaults();
    }

    static void testContainsExistingStringValue() {
        Properties p = new Properties();
        p.setProperty("k", "v");
        if (!p.contains("v")) {
            throw new RuntimeException("contains should return true for an existing value");
        }
    }

    static void testContainsMissingValue() {
        Properties p = new Properties();
        p.setProperty("k", "v");
        if (p.contains("missing")) {
            throw new RuntimeException("contains should return false for an absent value");
        }
        if (new Properties().contains("v")) {
            throw new RuntimeException("contains should return false on an empty Properties");
        }
    }

    static void testContainsNullThrowsNPE() {
        Properties p = new Properties();
        p.setProperty("k", "v");
        boolean thrown = false;
        try {
            p.contains(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("contains(null) should throw NullPointerException");
        }
    }

    static void testContainsNonStringValue() {
        Properties p = new Properties();
        p.put("k", Integer.valueOf(1));
        if (!p.contains(Integer.valueOf(1))) {
            throw new RuntimeException("contains should match a stored non-String value by equality");
        }
        if (p.contains("1")) {
            throw new RuntimeException("contains should not match a String against a stored Integer");
        }
    }

    static void testContainsIgnoresDefaults() {
        Properties defaults = new Properties();
        defaults.setProperty("d", "dv");
        Properties p = new Properties(defaults);
        if (p.contains("dv")) {
            throw new RuntimeException("contains should inspect only the own table, not the defaults");
        }
    }
}
