/*
 * @test
 * @summary Verify Properties.put returns previous values, stores entries and rejects null keys or values
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties p = new Properties();

        if (p.put("k", "v1") != null) {
            throw new RuntimeException("put on absent key must return null");
        }
        if (!"v1".equals(p.put("k", "v2"))) {
            throw new RuntimeException("put on existing key must return previous value");
        }
        if (!"v2".equals(p.getProperty("k"))) {
            throw new RuntimeException("getProperty must observe the value stored by put");
        }

        Object prev = p.put("n", Integer.valueOf(7));
        if (prev != null) {
            throw new RuntimeException("put of new non-String value must return null");
        }
        if (!Integer.valueOf(7).equals(p.get("n"))) {
            throw new RuntimeException("get must return the non-String value stored by put");
        }

        try {
            p.put(null, "x");
            throw new RuntimeException("put with null key must throw NullPointerException");
        } catch (NullPointerException expected) {
        }

        try {
            p.put("x", null);
            throw new RuntimeException("put with null value must throw NullPointerException");
        } catch (NullPointerException expected) {
        }

        if (p.containsKey("x")) {
            throw new RuntimeException("failed put with null value must not add an entry");
        }
    }
}
