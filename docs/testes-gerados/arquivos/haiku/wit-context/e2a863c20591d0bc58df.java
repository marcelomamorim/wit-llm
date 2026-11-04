/*
 * @test
 * @summary Witup-generated checks for Properties.setProperty
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties p = new Properties();
        if (p.setProperty("a", "1") != null) {
            throw new RuntimeException("expected null previous value for new key");
        }
        if (!"1".equals(p.getProperty("a"))) {
            throw new RuntimeException("value not stored");
        }
        Object prev = p.setProperty("a", "2");
        if (!"1".equals(prev)) {
            throw new RuntimeException("expected previous value \"1\" but got " + prev);
        }
        if (!"2".equals(p.getProperty("a"))) {
            throw new RuntimeException("value not replaced");
        }

        checkNullValueRejected(p);
        checkNullKeyRejected(p);

        Properties defaults = new Properties();
        defaults.setProperty("x", "dv");
        Properties withDefaults = new Properties(defaults);
        if (withDefaults.setProperty("x", "own") != null) {
            throw new RuntimeException("defaults must not be returned as previous value");
        }
        if (!"own".equals(withDefaults.getProperty("x"))) {
            throw new RuntimeException("own value not stored over defaults");
        }
    }

    private static void checkNullValueRejected(Properties p) {
        try {
            p.setProperty("k", null);
        } catch (NullPointerException e) {
            if (p.getProperty("k") != null) {
                throw new RuntimeException("entry inserted despite NPE");
            }
            return;
        }
        throw new RuntimeException("expected NullPointerException for null value");
    }

    private static void checkNullKeyRejected(Properties p) {
        try {
            p.setProperty(null, "v");
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null key");
    }
}
