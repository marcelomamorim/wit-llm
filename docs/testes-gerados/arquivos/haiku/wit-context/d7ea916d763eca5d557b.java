/*
 * @test
 * @summary WITUP baseline check: SubMap keySet iterator remove() must throw IllegalStateException when no element was returned by next() (or after a previous remove()).
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static ConcurrentSkipListMap<Integer, String> newMap() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 5; i++) {
            m.put(i, "v" + i);
        }
        return m;
    }

    public static void main(String[] args) {
        testRemoveBeforeNextThrows();
        testRemoveAfterNextRemovesMapping();
        testSecondRemoveWithoutNextThrows();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void testRemoveBeforeNextThrows() {
        ConcurrentSkipListMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.subMap(1, 4).keySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(m.size() == 5, "map must be unchanged after failed remove()");
    }

    private static void testRemoveAfterNextRemovesMapping() {
        ConcurrentSkipListMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.subMap(1, 4).keySet().iterator();
        Integer first = it.next();
        check(first != null && first.intValue() == 1, "first key of subMap(1,4) must be 1");
        it.remove();
        check(!m.containsKey(1), "remove() after next() must remove key 1 from backing map");
        check(m.containsKey(2), "key 2 must remain after removing key 1");
        check(m.size() == 4, "map size must be 4 after one removal");
    }

    private static void testSecondRemoveWithoutNextThrows() {
        ConcurrentSkipListMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.subMap(1, 4).keySet().iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove() without intervening next() must throw IllegalStateException");
        check(m.size() == 4, "map must not change after failed second remove()");
        check(it.next().intValue() == 2, "next() after remove must still return key 2");
        it.remove();
        check(!m.containsKey(2), "remove() after next() must remove key 2");
    }
}
