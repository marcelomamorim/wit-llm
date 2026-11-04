/*
 * @test
 * @summary Verifies observable behavior of TreeMap iterator next() through keySet().iterator()
 * @run main TreeMapWitupTest
 */

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNaturalOrderNext();
        testReverseOrderNext();
        testNextOnEmptyMapThrows();
        testNextAfterStructuralModificationThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNaturalOrderNext() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("b", 2);
        map.put("a", 1);
        map.put("c", 3);

        Iterator<String> it = map.keySet().iterator();
        check(it.hasNext(), "iterator should have elements");
        check("a".equals(it.next()), "first key should be a");
        check("b".equals(it.next()), "second key should be b");
        check("c".equals(it.next()), "third key should be c");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testReverseOrderNext() {
        TreeMap<String, Integer> map =
                new TreeMap<>(Collections.reverseOrder());
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        Iterator<String> it = map.keySet().iterator();
        check("c".equals(it.next()), "reverse first key should be c");
        check("b".equals(it.next()), "reverse second key should be b");
        check("a".equals(it.next()), "reverse third key should be a");
    }

    static void testNextOnEmptyMapThrows() {
        TreeMap<String, Integer> map = new TreeMap<>();
        Iterator<String> it = map.keySet().iterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty map iterator should throw NoSuchElementException");
    }

    static void testNextAfterStructuralModificationThrows() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);

        Iterator<String> it = map.keySet().iterator();
        check("a".equals(it.next()), "first key should be a");

        map.put("d", 4);
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "next() after structural modification should throw ConcurrentModificationException");
    }
}
