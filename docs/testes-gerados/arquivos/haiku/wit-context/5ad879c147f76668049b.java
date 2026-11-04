/*
 * @test
 * @summary Witup generated checks for Properties.put return values and null key/value rejection
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    public static void main(String[] args) {
        Properties p = new Properties();

        Object prev = p.put("k", "v1");
        check(prev == null, "first put must return null for a new key");

        prev = p.put("k", "v2");
        check("v1".equals(prev), "put must return the previous value");
        check("v2".equals(p.getProperty("k")), "put must replace the value");
        check(p.size() == 1, "size must be 1 after replacing a key");

        expectNpe(() -> p.put(null, "x"), "put with null key must throw NullPointerException");
        expectNpe(() -> p.put("k2", null), "put with null value must throw NullPointerException");
        expectNpe(() -> p.put(null, null), "put with null key and value must throw NullPointerException");

        check(p.size() == 1, "failed put must not add entries");
        check("v2".equals(p.getProperty("k")), "failed put must not change existing value");
        check(p.getProperty("k2") == null, "failed put with null value must not insert key");
    }

    private static void expectNpe(Runnable r, String msg) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(msg);
    }

    private static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }
}
