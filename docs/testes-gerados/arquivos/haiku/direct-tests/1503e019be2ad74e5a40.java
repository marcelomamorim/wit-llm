/*
 * @test
 * @summary Verifies Properties.replaceAll applies the function to each own entry and does not alter defaults
 * @run main PropertiesWitupTest
 */

import java.util.Locale;
import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties p = new Properties();
        p.setProperty("a", "one");
        p.setProperty("b", "two");
        int[] calls = {0};
        p.replaceAll((k, v) -> {
            calls[0]++;
            return ((String) v).toUpperCase(Locale.ROOT);
        });
        check(calls[0] == 2, "expected 2 invocations but was " + calls[0]);
        check("ONE".equals(p.getProperty("a")), "a should be ONE");
        check("TWO".equals(p.getProperty("b")), "b should be TWO");
        check(p.size() == 2, "size should remain 2");

        Properties p2 = new Properties();
        p2.setProperty("x", "1");
        p2.setProperty("y", "2");
        p2.replaceAll((k, v) -> k + "=" + v);
        check("x=1".equals(p2.getProperty("x")), "x should be x=1");
        check("y=2".equals(p2.getProperty("y")), "y should be y=2");

        Properties defaults = new Properties();
        defaults.setProperty("d", "x");
        Properties q = new Properties(defaults);
        q.setProperty("k", "v");
        q.replaceAll((k, v) -> "Y");
        check("Y".equals(q.getProperty("k")), "own entry should be replaced");
        check("x".equals(q.getProperty("d")), "default entry must not be affected");

        Properties empty = new Properties();
        empty.replaceAll((k, v) -> "z");
        check(empty.isEmpty(), "empty properties should remain empty");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
