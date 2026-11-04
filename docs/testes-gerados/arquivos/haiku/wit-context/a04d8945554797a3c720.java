/*
 * @test
 * @summary Verify observable behavior of Properties.containsValue, including NullPointerException for null values
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties empty = new Properties();
        check(!empty.containsValue("x"), "empty properties must not contain value");

        Properties p = new Properties();
        p.setProperty("a", "1");
        check(p.containsValue("1"), "existing value must be found");
        check(!p.containsValue("2"), "absent value must not be found");

        boolean thrown = false;
        try {
            p.containsValue(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "containsValue(null) must throw NullPointerException");

        Properties defaults = new Properties();
        defaults.setProperty("d", "dv");
        Properties q = new Properties(defaults);
        check(!q.containsValue("dv"), "default values must not be searched by containsValue");
        q.setProperty("k", "v");
        check(q.containsValue("v"), "own value must be found");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
