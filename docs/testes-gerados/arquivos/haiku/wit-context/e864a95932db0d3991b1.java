/*
 * @test
 * @summary Behavioral checks for ConcurrentSkipListMap.higherEntry including null key rejection, boundaries, reverse comparator and snapshot entries
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        check(empty.higherEntry(1) == null, "empty map must return null");

        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(3, "c");
        m.put(5, "e");

        Map.Entry<Integer, String> e = m.higherEntry(0);
        check(e != null && e.getKey() == 1 && "a".equals(e.getValue()), "higherEntry(0) must be 1->a");

        e = m.higherEntry(1);
        check(e != null && e.getKey() == 3, "higherEntry(1) must be key 3");

        e = m.higherEntry(4);
        check(e != null && e.getKey() == 5, "higherEntry(4) must be key 5");

        check(m.higherEntry(5) == null, "higherEntry(max key) must return null");
        check(m.higherEntry(6) == null, "higherEntry(above max) must return null");

        try {
            m.higherEntry(null);
            throw new RuntimeException("higherEntry(null) must throw NullPointerException");
        } catch (NullPointerException ex) {
            // expected
        }

        ConcurrentSkipListMap<Integer, String> r = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        r.put(1, "a");
        r.put(3, "c");
        r.put(5, "e");
        e = r.higherEntry(5);
        check(e != null && e.getKey() == 3, "reverse order higherEntry(5) must be key 3");
        check(r.higherEntry(1) == null, "reverse order higherEntry(min key) must return null");

        Map.Entry<Integer, String> snap = m.higherEntry(0);
        m.put(1, "z");
        check("a".equals(snap.getValue()), "returned entry must be a snapshot of the mapping");
    }
}
