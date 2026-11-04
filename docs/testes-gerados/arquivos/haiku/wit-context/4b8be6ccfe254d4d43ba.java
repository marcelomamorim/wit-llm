/*
 * @test
 * @summary Verifies TreeMap entry iterator next() ordering, NoSuchElementException on empty or exhausted iteration, and ConcurrentModificationException after a structural modification
 * @run main TreeMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapNextThrowsNoSuchElement();
        testExhaustedIteratorNextThrowsNoSuchElement();
        testEntriesReturnedInKeyOrder();
        testConcurrentModificationAfterStructuralChange();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyMapNextThrowsNoSuchElement() {
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException expected) {
            thrown = true;
        }
        check(thrown, "next on empty map must throw NoSuchElementException");
    }

    static void testExhaustedIteratorNextThrowsNoSuchElement() {
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        map.put(1, 10);
        map.put(2, 20);
        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
        it.next();
        it.next();
        check(!it.hasNext(), "iterator should be exhausted");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException expected) {
            thrown = true;
        }
        check(thrown, "next after exhaustion must throw NoSuchElementException");
    }

    static void testEntriesReturnedInKeyOrder() {
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        map.put(3, 30);
        map.put(1, 10);
        map.put(2, 20);
        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
        for (int i = 1; i <= 3; i++) {
            Map.Entry<Integer, Integer> e = it.next();
            check(e.getKey() == i, "unexpected key order");
            check(e.getValue() == i * 10, "unexpected value for key");
        }
        check(!it.hasNext(), "iterator should have no more elements");
    }

    static void testConcurrentModificationAfterStructuralChange() {
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        map.put(1, 10);
        map.put(2, 20);
        map.put(3, 30);
        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
        it.next();
        map.put(4, 40);
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException expected) {
            thrown = true;
        }
        check(thrown, "next after structural modification must throw ConcurrentModificationException");
    }
}
