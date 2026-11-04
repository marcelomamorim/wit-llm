/*
 * @test
 * @summary WITUP-derived check that ConcurrentSkipListMap value iterators throw NoSuchElementException when exhausted
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapValueIteratorThrows();
        testExhaustedValueIteratorThrows();
        testValuesIteratedInKeyOrder();
    }

    static void testEmptyMapValueIteratorThrows() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Iterator<String> it = map.values().iterator();
        if (it.hasNext()) {
            throw new AssertionError("empty map iterator should have no elements");
        }
        expectNoSuchElement(it);
    }

    static void testExhaustedValueIteratorThrows() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(2, "b");
        Iterator<String> it = map.values().iterator();
        if (!"a".equals(it.next())) {
            throw new AssertionError("first value mismatch");
        }
        if (!"b".equals(it.next())) {
            throw new AssertionError("second value mismatch");
        }
        if (it.hasNext()) {
            throw new AssertionError("iterator should be exhausted");
        }
        expectNoSuchElement(it);
        expectNoSuchElement(it);
    }

    static void testValuesIteratedInKeyOrder() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(3, "c");
        map.put(1, "a");
        map.put(2, "b");
        Iterator<String> it = map.values().iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        if (!"abc".equals(sb.toString())) {
            throw new AssertionError("expected values in key order abc but got " + sb);
        }
        expectNoSuchElement(it);
    }

    static void expectNoSuchElement(Iterator<String> it) {
        try {
            it.next();
        } catch (NoSuchElementException e) {
            return;
        }
        throw new AssertionError("expected NoSuchElementException");
    }
}
