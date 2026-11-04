/*
 * @test
 * @summary Verifies that the iterator returned by LinkedHashMap.keySet() uses the inherited next(), which returns the key of the next node in insertion or access order and throws NoSuchElementException when exhausted.
 * @run main LinkedHashMapWitupTest
 */

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

public class LinkedHashMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertionOrderNext() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Iterator<String> it = map.keySet().iterator();
        check("a".equals(it.next()), "first next() should return 'a'");
        check("b".equals(it.next()), "second next() should return 'b'");
        check("c".equals(it.next()), "third next() should return 'c'");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testReinsertKeepsOrder() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("a", 10);
        Iterator<String> it = map.keySet().iterator();
        check("a".equals(it.next()), "re-inserted key must keep its position");
        check("b".equals(it.next()), "second key should be 'b'");
    }

    static void testAccessOrderNext() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>(16, 0.75f, true);
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.get("a");
        Iterator<String> it = map.keySet().iterator();
        check("b".equals(it.next()), "in access order, least recently accessed key should come first");
        check("c".equals(it.next()), "second key in access order should be 'c'");
        check("a".equals(it.next()), "most recently accessed key should come last");
    }

    static void testNextOnEmptyThrows() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        Iterator<String> it = map.keySet().iterator();
        try {
            it.next();
            throw new RuntimeException("next() on empty map iterator should throw");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        testInsertionOrderNext();
        testReinsertKeepsOrder();
        testAccessOrderNext();
        testNextOnEmptyThrows();
    }
}
