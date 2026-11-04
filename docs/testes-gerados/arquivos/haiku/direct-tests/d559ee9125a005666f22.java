/*
 * @test
 * @summary Behavioral tests for ConcurrentSkipListMap.remove(Object)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);

        Integer removed = m.remove("b");
        check(removed != null && removed.intValue() == 2, "remove existing key must return its value");
        check(!m.containsKey("b"), "removed key must no longer be present");
        check(m.size() == 2, "size must decrease after successful removal");

        check(m.remove("b") == null, "removing an absent key must return null");
        check(m.size() == 2, "removing an absent key must not change size");

        check(m.remove("zzz") == null, "removing an unknown key must return null");
        check(m.size() == 2, "size must be unchanged after removing unknown key");

        check(m.remove(new String("a")) != null, "removal must match keys by equality");
        check(m.get("a") == null, "equal key must be removed");
        check(m.size() == 1, "size must reflect removal by equal key");

        boolean npe = false;
        try {
            m.remove(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "remove(null) must throw NullPointerException");

        ConcurrentSkipListMap<Integer, String> r = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        r.put(1, "one");
        r.put(2, "two");
        check("one".equals(r.remove(1)), "remove must work with a custom comparator");
        check(r.get(1) == null, "key removed under custom comparator must be absent");
        check("two".equals(r.get(2)), "other entries must remain after removal");
        check(r.remove(1) == null, "second removal under custom comparator must return null");
    }
}
