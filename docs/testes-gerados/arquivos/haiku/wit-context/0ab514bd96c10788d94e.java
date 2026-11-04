/*
 * @test
 * @summary Verify NoSuchElementException from exhausted key iterators of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapKeyIterator();
        testExhaustedKeyIterator();
        testEmptySubMapKeyIterator();
        testEmptyDescendingKeyIterator();
        System.out.println("PASS");
    }

    static void expectNoSuchElement(Iterator<Integer> it, String label) {
        if (it.hasNext()) {
            throw new RuntimeException(label + ": hasNext() returned true");
        }
        try {
            Integer v = it.next();
            throw new RuntimeException(label + ": next() returned " + v);
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testEmptyMapKeyIterator() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        expectNoSuchElement(m.keySet().iterator(), "empty map");
    }

    static void testExhaustedKeyIterator() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        Iterator<Integer> it = m.keySet().iterator();
        if (!it.hasNext() || it.next().intValue() != 1) {
            throw new RuntimeException("first key mismatch");
        }
        if (!it.hasNext() || it.next().intValue() != 2) {
            throw new RuntimeException("second key mismatch");
        }
        expectNoSuchElement(it, "exhausted iterator");
    }

    static void testEmptySubMapKeyIterator() {
        ConcurrentNavigableMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(10, "x");
        m.put(20, "y");
        ConcurrentNavigableMap<Integer, String> sub = m.subMap(1, true, 5, false);
        expectNoSuchElement(sub.keySet().iterator(), "empty submap");
    }

    static void testEmptyDescendingKeyIterator() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        expectNoSuchElement(m.descendingKeySet().iterator(), "empty descending");
    }
}
