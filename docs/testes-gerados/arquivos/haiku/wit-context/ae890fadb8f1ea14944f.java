/*
 * @test
 * @summary Witup-generated checks for Properties.remove(Object, Object)
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        Properties p = new Properties();
        p.setProperty("a", "1");

        if (p.remove("a", "2")) {
            throw new RuntimeException("remove with mismatched value returned true");
        }
        if (!"1".equals(p.getProperty("a"))) {
            throw new RuntimeException("entry changed after mismatched remove");
        }

        if (p.remove("a", null)) {
            throw new RuntimeException("remove with null value returned true");
        }
        if (!"1".equals(p.getProperty("a"))) {
            throw new RuntimeException("entry removed by null value remove");
        }

        if (p.remove("missing", "x")) {
            throw new RuntimeException("remove of absent key returned true");
        }

        if (!p.remove("a", "1")) {
            throw new RuntimeException("remove with matching mapping returned false");
        }
        if (p.containsKey("a")) {
            throw new RuntimeException("key still present after successful remove");
        }

        boolean thrown = false;
        try {
            p.remove(null, "x");
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("null key did not throw NullPointerException");
        }

        Properties defaults = new Properties();
        defaults.setProperty("k", "v");
        Properties q = new Properties(defaults);
        if (q.remove("k", "v")) {
            throw new RuntimeException("remove consulted defaults");
        }
        if (!"v".equals(q.getProperty("k"))) {
            throw new RuntimeException("default value affected by remove on instance");
        }
    }
}
