/*
 * @test
 * @summary Verifies ConcurrentSkipListMap value iterator next() returns values in key order and throws NoSuchElementException when exhausted
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
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

    static void testNextReturnsValuesInKeyOrder() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(3, "c");
        map.put(1, "a");
        map.put(2, "b");
        Iterator<String> it = map.values().iterator();
        List<String> seen = new ArrayList<>();
        while (it.hasNext()) {
            seen.add(it.next());
        }
        check(seen.size() == 3, "expected 3 values but got " + seen.size());
        check("a".equals(seen.get(0)), "first value should be a but was " + seen.get(0));
        check("b".equals(seen.get(1)), "second value should be b but was " + seen.get(1));
        check("c".equals(seen.get(2)), "third value should be c but was " + seen.get(2));
    }

    static void testNextOnSingleElementMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "only");
        Iterator<String> it = map.values().iterator();
        check(it.hasNext(), "iterator should have one element");
        String v = it.next();
        check("only".equals(v), "expected only but was " + v);
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testNextOnExhaustedIteratorThrows() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "x");
        Iterator<String> it = map.values().iterator();
        it.next();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on exhausted iterator should throw NoSuchElementException");
    }

    static void testNextOnEmptyMapThrows() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        Iterator<String> it = map.values().iterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty map iterator should throw NoSuchElementException");
    }

    public static void main(String[] args) {
        testNextReturnsValuesInKeyOrder();
        testNextOnSingleElementMap();
        testNextOnExhaustedIteratorThrows();
        testNextOnEmptyMapThrows();
    }
}
