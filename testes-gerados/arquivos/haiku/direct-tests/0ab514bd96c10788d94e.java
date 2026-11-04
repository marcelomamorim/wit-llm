/*
 * @test
 * @summary Tests observable behavior of ConcurrentSkipListMap key iterator next()
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAscendingKeyIteration() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(3, "c");
        m.put(1, "a");
        m.put(2, "b");
        List<Integer> keys = new ArrayList<>();
        Iterator<Integer> it = m.keySet().iterator();
        while (it.hasNext()) {
            keys.add(it.next());
        }
        check(keys.equals(Arrays.asList(1, 2, 3)), "ascending keys mismatch: " + keys);
    }

    static void testNextAfterExhaustionThrows() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        Iterator<Integer> it = m.keySet().iterator();
        check(it.next() == 1, "first next() should return 1");
        check(!it.hasNext(), "iterator should be exhausted");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on exhausted iterator must throw NoSuchElementException");
    }

    static void testNextOnEmptyMapThrows() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        Iterator<Integer> it = m.keySet().iterator();
        check(!it.hasNext(), "empty map iterator should have no elements");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty map iterator must throw NoSuchElementException");
    }

    static void testDescendingKeyIteration() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        List<Integer> keys = new ArrayList<>();
        Iterator<Integer> it = m.descendingKeySet().iterator();
        while (it.hasNext()) {
            keys.add(it.next());
        }
        check(keys.equals(Arrays.asList(3, 2, 1)), "descending keys mismatch: " + keys);
    }

    static void testSingleElement() {
        ConcurrentSkipListMap<String, Integer> m = new ConcurrentSkipListMap<>();
        m.put("only", 42);
        Iterator<String> it = m.keySet().iterator();
        check("only".equals(it.next()), "single key should be returned by next()");
        check(!it.hasNext(), "no further keys expected");
    }

    public static void main(String[] args) {
        testAscendingKeyIteration();
        testNextAfterExhaustionThrows();
        testNextOnEmptyMapThrows();
        testDescendingKeyIteration();
        testSingleElement();
    }
}
