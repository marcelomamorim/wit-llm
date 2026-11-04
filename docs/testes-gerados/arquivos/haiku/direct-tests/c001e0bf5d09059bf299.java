/*
 * @test
 * @summary Verify TreeMap sub-map headMap(K, boolean) range checks and bounded results through public API
 * @run main TreeMapWitupTest
 */

import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        TreeMap<Integer, String> m = new TreeMap<>();
        for (int i = 1; i <= 5; i++) {
            m.put(i, "v" + i);
        }

        NavigableMap<Integer, String> sub = m.headMap(3, false);
        check(sub.keySet().toString().equals("[1, 2]"),
              "headMap(3,false) keys expected [1, 2] but was " + sub.keySet());

        NavigableMap<Integer, String> inclusive = sub.headMap(2, true);
        check(inclusive.keySet().toString().equals("[1, 2]"),
              "sub.headMap(2,true) keys expected [1, 2] but was " + inclusive.keySet());
        check("v2".equals(inclusive.get(2)), "value for key 2 should be v2");

        NavigableMap<Integer, String> exclusive = sub.headMap(2, false);
        check(exclusive.keySet().toString().equals("[1]"),
              "sub.headMap(2,false) keys expected [1] but was " + exclusive.keySet());
        check(!exclusive.containsKey(2), "key 2 must not be in exclusive headMap");

        boolean thrown = false;
        try {
            sub.headMap(5, true);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "headMap with key above range must throw IllegalArgumentException");

        NavigableMap<Integer, String> full = m.headMap(4, true);
        check(full.keySet().toString().equals("[1, 2, 3, 4]"),
              "headMap(4,true) keys expected [1, 2, 3, 4] but was " + full.keySet());
    }
}
