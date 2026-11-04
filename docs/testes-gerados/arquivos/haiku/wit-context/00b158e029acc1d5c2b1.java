/*
 * @test
 * @summary Tests LinkedHashMap key iterator next() for exhaustion, concurrent modification and ordering
 * @run main LinkedHashMapWitupTest
 */

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapNextThrowsNoSuchElement();
        testExhaustedIteratorNextThrowsNoSuchElement();
        testConcurrentModificationAfterPut();
        testIteratorRemoveDoesNotCauseConcurrentModification();
        testInsertionOrderFromKeyIterator();
        testAccessOrderFromKeyIterator();
    }

    static void testEmptyMapNextThrowsNoSuchElement() {
        Map<String, Integer> map = new LinkedHashMap<>();
        expectNoSuchElement(map.keySet().iterator());
    }

    static void testExhaustedIteratorNextThrowsNoSuchElement() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        Iterator<String> it = map.keySet().iterator();
        check("a".equals(it.next()), "first key");
        expectNoSuchElement(it);
    }

    static void testConcurrentModificationAfterPut() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Iterator<String> it = map.keySet().iterator();
        check("a".equals(it.next()), "first key before modification");
        map.put("c", 3);
        expectConcurrentModification(it);
    }

    static void testIteratorRemoveDoesNotCauseConcurrentModification() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Iterator<String> it = map.keySet().iterator();
        check("a".equals(it.next()), "first key");
        it.remove();
        check("b".equals(it.next()), "second key after remove");
        check(!it.hasNext(), "no more keys");
        check(map.keySet().size() == 1 && map.containsKey("b"), "map state after remove");
    }

    static void testInsertionOrderFromKeyIterator() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("z", 1);
        map.put("x", 2);
        map.put("y", 3);
        map.put("z", 4);
        Iterator<String> it = map.keySet().iterator();
        check("z".equals(it.next()), "order z");
        check("x".equals(it.next()), "order x");
        check("y".equals(it.next()), "order y");
        expectNoSuchElement(it);
    }

    static void testAccessOrderFromKeyIterator() {
        Map<String, Integer> map = new LinkedHashMap<>(16, 0.75f, true);
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.get("a");
        Iterator<String> it = map.keySet().iterator();
        check("b".equals(it.next()), "access order first");
        check("c".equals(it.next()), "access order second");
        check("a".equals(it.next()), "access order third");
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

    static void expectConcurrentModification(Iterator<String> it) {
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            return;
        }
        throw new AssertionError("expected ConcurrentModificationException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
