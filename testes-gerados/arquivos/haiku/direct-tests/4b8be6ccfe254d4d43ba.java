/*
 * @test
 * @summary Verifies TreeMap entry iterator next() returns entries in order and throws NoSuchElementException when exhausted
 * @run main TreeMapWitupTest
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsAscendingEntries();
        testNextOnEmptyMapThrows();
        testNextWithReverseComparator();
        testNextReturnsKeyAndValue();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNextReturnsAscendingEntries() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(3, "c");
        map.put(1, "a");
        map.put(2, "b");
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        check(it.hasNext(), "iterator should have first element");
        check(it.next().getKey() == 1, "first key should be 1");
        check(it.next().getKey() == 2, "second key should be 2");
        check(it.next().getKey() == 3, "third key should be 3");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testNextOnEmptyMapThrows() {
        TreeMap<Integer, String> map = new TreeMap<>();
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        check(!it.hasNext(), "empty map iterator should have no elements");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty iterator should throw NoSuchElementException");
    }

    static void testNextWithReverseComparator() {
        TreeMap<Integer, String> map = new TreeMap<>(Collections.reverseOrder());
        map.put(1, "a");
        map.put(3, "c");
        map.put(2, "b");
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        check(it.next().getKey() == 3, "reverse order first key should be 3");
        check(it.next().getKey() == 2, "reverse order second key should be 2");
        check(it.next().getKey() == 1, "reverse order third key should be 1");
        check(!it.hasNext(), "reverse order iterator should be exhausted");
    }

    static void testNextReturnsKeyAndValue() {
        TreeMap<String, Integer> source = new TreeMap<>();
        source.put("x", 10);
        source.put("y", 20);
        TreeMap<String, Integer> copy = new TreeMap<>(source);
        Iterator<Map.Entry<String, Integer>> it = copy.entrySet().iterator();
        Map.Entry<String, Integer> first = it.next();
        check(first.getKey().equals("x"), "first key should be x");
        check(first.getValue().intValue() == 10, "first value should be 10");
        Map.Entry<String, Integer> second = it.next();
        check(second.getKey().equals("y"), "second key should be y");
        check(second.getValue().intValue() == 20, "second value should be 20");
    }
}
