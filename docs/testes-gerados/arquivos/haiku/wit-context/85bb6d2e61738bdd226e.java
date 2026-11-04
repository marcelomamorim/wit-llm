/*
 * @test
 * @summary Checks LinkedHashMap entry iterator next() ordering and its NoSuchElementException and ConcurrentModificationException behavior
 * @run main LinkedHashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testInsertionOrderNext();
        testNextOnExhaustedIteratorThrows();
        testNextAfterStructuralModificationThrows();
        testAccessOrderNext();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertionOrderNext() {
        LinkedHashMap<String, Integer> m = new LinkedHashMap<>();
        m.put("c", 3);
        m.put("a", 1);
        m.put("b", 2);
        Iterator<Map.Entry<String, Integer>> it = m.entrySet().iterator();

        Map.Entry<String, Integer> e = it.next();
        check("c".equals(e.getKey()) && e.getValue() == 3, "first entry should be c=3");
        e = it.next();
        check("a".equals(e.getKey()) && e.getValue() == 1, "second entry should be a=1");
        e = it.next();
        check("b".equals(e.getKey()) && e.getValue() == 2, "third entry should be b=2");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testNextOnExhaustedIteratorThrows() {
        LinkedHashMap<String, Integer> m = new LinkedHashMap<>();
        m.put("x", 1);
        Iterator<Map.Entry<String, Integer>> it = m.entrySet().iterator();
        it.next();
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testNextAfterStructuralModificationThrows() {
        LinkedHashMap<String, Integer> m = new LinkedHashMap<>();
        m.put("a", 1);
        m.put("b", 2);
        Iterator<Map.Entry<String, Integer>> it = m.entrySet().iterator();
        it.next();
        m.put("z", 26);
        try {
            it.next();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testAccessOrderNext() {
        LinkedHashMap<String, Integer> m = new LinkedHashMap<>(16, 0.75f, true);
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        m.get("a");
        Iterator<Map.Entry<String, Integer>> it = m.entrySet().iterator();
        check("b".equals(it.next().getKey()), "access order: first should be b");
        check("c".equals(it.next().getKey()), "access order: second should be c");
        check("a".equals(it.next().getKey()), "access order: third should be a");
        check(!it.hasNext(), "access order: iterator should be exhausted");
    }
}
