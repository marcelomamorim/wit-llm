/*
 * @test
 * @summary Tests ConcurrentSkipListMap.higherEntry observable behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testEmptyMap();
        testBasicOrdering();
        testReverseComparator();
        testCopyConstructor();
        testSnapshotEntry();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyMap() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        check(m.higherEntry(0) == null, "higherEntry on empty map must return null");
    }

    static void testBasicOrdering() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(3, "c");
        m.put(5, "e");

        Map.Entry<Integer, String> e = m.higherEntry(0);
        check(e != null && e.getKey() == 1 && "a".equals(e.getValue()), "higher than 0 should be 1");

        e = m.higherEntry(1);
        check(e != null && e.getKey() == 3 && "c".equals(e.getValue()), "strictly higher than 1 should be 3");

        e = m.higherEntry(2);
        check(e != null && e.getKey() == 3, "higher than absent key 2 should be 3");

        e = m.higherEntry(4);
        check(e != null && e.getKey() == 5, "higher than absent key 4 should be 5");

        check(m.higherEntry(5) == null, "no key strictly higher than the maximum");
        check(m.higherEntry(6) == null, "no key higher than 6");
    }

    static void testReverseComparator() {
        ConcurrentSkipListMap<Integer, String> m =
            new ConcurrentSkipListMap<>(Comparator.<Integer>reverseOrder());
        m.put(1, "a");
        m.put(3, "c");
        m.put(5, "e");

        Map.Entry<Integer, String> e = m.higherEntry(3);
        check(e != null && e.getKey() == 1, "in reverse order, higher than 3 should be 1");

        e = m.higherEntry(5);
        check(e != null && e.getKey() == 3, "in reverse order, higher than 5 should be 3");

        check(m.higherEntry(1) == null, "in reverse order, nothing is higher than 1");
    }

    static void testCopyConstructor() {
        TreeMap<Integer, String> source = new TreeMap<>();
        source.put(2, "b");
        source.put(4, "d");
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>(source);

        Map.Entry<Integer, String> e = m.higherEntry(2);
        check(e != null && e.getKey() == 4 && "d".equals(e.getValue()), "copied map: higher than 2 should be 4");

        e = m.higherEntry(3);
        check(e != null && e.getKey() == 4, "copied map: higher than absent 3 should be 4");

        check(m.higherEntry(4) == null, "copied map: nothing higher than 4");
    }

    static void testSnapshotEntry() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(3, "c");

        Map.Entry<Integer, String> snapshot = m.higherEntry(1);
        m.put(3, "z");

        check("c".equals(snapshot.getValue()), "returned entry must be a snapshot of the old mapping");
        Map.Entry<Integer, String> current = m.higherEntry(1);
        check(current != null && "z".equals(current.getValue()), "new lookup must reflect updated value");
    }
}
