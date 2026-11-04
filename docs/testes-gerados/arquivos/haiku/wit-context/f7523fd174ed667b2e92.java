/*
 * @test
 * @summary Checks Entry.getValue() through the public entrySet iterator of IdentityHashMap, including null key and null value
 * @run main IdentityHashMapWitupTest
 */

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

public class IdentityHashMapWitupTest {

    public static void main(String[] args) {
        IdentityHashMap<String, Integer> m = new IdentityHashMap<>();
        String k1 = new String("a");
        String k2 = new String("a");
        m.put(k1, 1);
        m.put(k2, 2);
        m.put(null, null);

        int count = 0;
        boolean sawK2 = false;
        Iterator<Map.Entry<String, Integer>> it = m.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> e = it.next();
            Integer v = e.getValue();
            check(v == m.get(e.getKey()) || (v != null && v.equals(m.get(e.getKey()))));
            if (e.getKey() == k2) {
                check(v != null && v.intValue() == 2);
                sawK2 = true;
            }
            if (e.getKey() == null) {
                check(v == null);
            }
            count++;
        }
        check(count == 3);
        check(sawK2);
    }

    private static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Assertion failed");
        }
    }
}
