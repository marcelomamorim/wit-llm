/*
 * @test
 * @summary Witup-derived checks for TreeMap values iterator next()
 * @run main TreeMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testValuesIteratedInKeyOrder();
        testNextOnEmptyMapThrows();
        testNextAfterExhaustionThrows();
        testNextAfterStructuralModificationThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValuesIteratedInKeyOrder() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(3, 30);
        map.put(1, 10);
        map.put(2, 20);
        Iterator<Integer> it = map.values().iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next()).append(',');
        }
        check("10,20,30,".equals(sb.toString()), "unexpected order: " + sb);
    }

    static void testNextOnEmptyMapThrows() {
        Iterator<Integer> it = new TreeMap<Integer, Integer>().values().iterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty map did not throw NoSuchElementException");
    }

    static void testNextAfterExhaustionThrows() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(1, 10);
        Iterator<Integer> it = map.values().iterator();
        check(it.next() == 10, "first next() returned unexpected value");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() after exhaustion did not throw NoSuchElementException");
    }

    static void testNextAfterStructuralModificationThrows() {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(1, 10);
        map.put(2, 20);
        Iterator<Integer> it = map.values().iterator();
        map.put(3, 30);
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "next() after structural modification did not throw ConcurrentModificationException");
    }
}
