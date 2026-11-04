/*
 * @test
 * @summary Tests observable behavior of Properties.putIfAbsent
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties p = new Properties();
        Object r = p.putIfAbsent("a", "1");
        if (r != null) {
            throw new RuntimeException("expected null for absent key, got " + r);
        }
        if (!"1".equals(p.getProperty("a"))) {
            throw new RuntimeException("value not inserted");
        }

        r = p.putIfAbsent("a", "2");
        if (!"1".equals(r)) {
            throw new RuntimeException("expected previous value 1, got " + r);
        }
        if (!"1".equals(p.getProperty("a"))) {
            throw new RuntimeException("existing value was replaced");
        }

        Properties defaults = new Properties();
        defaults.setProperty("k", "d");
        Properties q = new Properties(defaults);
        r = q.putIfAbsent("k", "v");
        if (r != null) {
            throw new RuntimeException("defaults must not affect putIfAbsent, got " + r);
        }
        if (!"v".equals(q.getProperty("k"))) {
            throw new RuntimeException("expected inserted value v");
        }

        p.putIfAbsent("n", Integer.valueOf(5));
        if (!Integer.valueOf(5).equals(p.get("n"))) {
            throw new RuntimeException("non-String value not stored");
        }
    }
}
