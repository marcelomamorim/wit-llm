/*
 * @test
 * @summary Witup-generated checks for HashMap value iterator next() exception paths
 * @run main HashMapWitupTest
 */

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNormalIteration();
        testNextOnExhaustedIteratorThrowsNoSuchElement();
        testNextAfterStructuralModificationThrowsConcurrentModification();
    }

    static void testNormalIteration() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Collection<Integer> values = map.values();
        Iterator<Integer> it = values.iterator();
        int sum = 0;
        int count = 0;
        while (it.hasNext()) {
            sum += it.next();
            count++;
        }
        check(count == 2, "expected 2 values, got " + count);
        check(sum == 3, "expected sum 3, got " + sum);
    }

    static void testNextOnExhaustedIteratorThrowsNoSuchElement() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        Iterator<Integer> it = map.values().iterator();
        Integer first = it.next();
        check(first == 1, "expected first value 1, got " + first);
        try {
            it.next();
            throw new AssertionError("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testNextAfterStructuralModificationThrowsConcurrentModification() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Iterator<Integer> it = map.values().iterator();
        it.next();
        map.put("c", 3);
        try {
            it.next();
            throw new AssertionError("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
