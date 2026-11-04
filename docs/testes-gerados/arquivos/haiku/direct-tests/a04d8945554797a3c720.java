/*
 * @test
 * @summary Black-box checks for Properties.containsValue delegating to the internal map
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        Properties empty = new Properties();
        check(!empty.containsValue("x"), "empty properties must not contain value");

        Properties p = new Properties();
        p.setProperty("a", "x");
        check(p.containsValue("x"), "value set via setProperty must be found");
        check(!p.containsValue("y"), "absent value must not be found");

        p.put("n", Integer.valueOf(42));
        check(p.containsValue(Integer.valueOf(42)), "non-String value inserted by put must be found");

        p.remove("a");
        check(!p.containsValue("x"), "removed value must no longer be found");

        Properties defaults = new Properties();
        defaults.setProperty("d", "dv");
        Properties withDefaults = new Properties(defaults);
        check(!withDefaults.containsValue("dv"), "containsValue must not search the defaults");

        boolean npe = false;
        try {
            p.containsValue(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "containsValue(null) must throw NullPointerException");
    }
}
