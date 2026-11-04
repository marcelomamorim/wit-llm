/*
 * @test
 * @summary Witup-generated behavioral tests for Properties.replaceAll
 * @run main PropertiesWitupTest
 */

import java.util.Properties;
import java.util.function.BiFunction;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNPE();
        testReplacesAllValues();
        testEmptyPropertiesIsNoOp();
        testDefaultsAreNotModified();
    }

    static void testNullFunctionThrowsNPE() {
        Properties p = new Properties();
        p.setProperty("a", "1");
        boolean thrown = false;
        try {
            p.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null function");
        }
        if (!"1".equals(p.getProperty("a"))) {
            throw new RuntimeException("Value changed after failed replaceAll");
        }
    }

    static void testReplacesAllValues() {
        Properties p = new Properties();
        p.setProperty("a", "1");
        p.setProperty("b", "2");
        BiFunction<Object, Object, Object> f = (k, v) -> k + "=" + v;
        p.replaceAll(f);
        check("a=1", p.getProperty("a"));
        check("b=2", p.getProperty("b"));
        if (p.size() != 2) {
            throw new RuntimeException("Unexpected size: " + p.size());
        }
    }

    static void testEmptyPropertiesIsNoOp() {
        Properties p = new Properties();
        BiFunction<Object, Object, Object> f = (k, v) -> "x";
        p.replaceAll(f);
        if (p.size() != 0) {
            throw new RuntimeException("Expected empty properties, size: " + p.size());
        }
    }

    static void testDefaultsAreNotModified() {
        Properties defaults = new Properties();
        defaults.setProperty("d", "default");
        Properties p = new Properties(defaults);
        p.setProperty("a", "1");
        BiFunction<Object, Object, Object> f = (k, v) -> "changed";
        p.replaceAll(f);
        check("changed", p.getProperty("a"));
        check("default", p.getProperty("d"));
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }
}
