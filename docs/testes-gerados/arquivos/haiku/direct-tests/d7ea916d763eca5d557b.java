/*
 * @test
 * @summary ConcurrentSkipListMap iterator remove(): IllegalStateException without a preceding next(), and removal of the last returned mapping
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrows();
        testRemoveAfterNextRemovesKey();
        testDoubleRemoveThrows();
        testEntrySetIteratorRemove();
        testValuesIteratorRemove();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveBeforeNextThrows() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        Iterator<Integer> it = m.keySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() must throw IllegalStateException");
        check(m.size() == 2, "map must be unchanged after failed remove()");
    }

    static void testRemoveAfterNextRemovesKey() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        Iterator<Integer> it = m.keySet().iterator();
        Integer k = it.next();
        it.remove();
        check(!m.containsKey(k), "remove() must remove the last returned key");
        check(m.size() == 2, "size must decrease by one");
        check(m.containsKey(2) && m.containsKey(3), "other mappings must remain");
    }

    static void testDoubleRemoveThrows() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        Iterator<Integer> it = m.keySet().iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove() without next() must throw IllegalStateException");
        check(m.size() == 1, "only one removal must have happened");
    }

    static void testEntrySetIteratorRemove() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        Iterator<Map.Entry<Integer, String>> it = m.entrySet().iterator();
        Map.Entry<Integer, String> e = it.next();
        Integer k = e.getKey();
        it.remove();
        check(!m.containsKey(k), "entrySet iterator remove() must remove the mapping");
        check(m.size() == 1, "size must decrease by one");
    }

    static void testValuesIteratorRemove() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        Iterator<String> it = m.values().iterator();
        String v = it.next();
        it.remove();
        check(!m.containsValue(v), "values iterator remove() must remove the mapping");
        check(m.size() == 1, "size must decrease by one");
    }
}
