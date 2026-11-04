/*
 * @test
 * @summary Verify ConcurrentSkipListMap.containsValue observable behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();

        check(!m.containsValue(1), "empty map must not contain value 1");

        m.put("a", 1);
        m.put("b", 2);

        check(m.containsValue(1), "value 1 should be found");
        check(m.containsValue(Integer.valueOf(2)), "value 2 should be found");
        check(!m.containsValue(3), "absent value 3 should not be found");
        check(!m.containsValue("1"), "value of a different type should not be found");

        m.put("c", Integer.valueOf(1000));
        check(m.containsValue(new StringBuilder("x").length() + 999), "equal-valued Integer should be found");

        m.remove("a");
        check(!m.containsValue(1), "removed value should no longer be found");

        m.put("b", 5);
        check(!m.containsValue(2), "replaced value should no longer be found");
        check(m.containsValue(5), "new value should be found");

        boolean npe = false;
        try {
            m.containsValue(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null argument must throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
