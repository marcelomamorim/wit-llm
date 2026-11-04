/*
 * @test
 * @summary Checks LinkedHashMap value iterator next() ordering through public API
 * @run main LinkedHashMapWitupTest
 */

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInsertionOrderNext() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Iterator<Integer> it = map.values().iterator();
        check(it.hasNext(), "iterator should have elements");
        check(it.next() == 1, "first next() should return 1");
        check(it.next() == 2, "second next() should return 2");
        check(it.next() == 3, "third next() should return 3");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testNullValueNext() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("k", null);
        Iterator<String> it = map.values().iterator();
        check(it.hasNext(), "iterator should have one element");
        check(it.next() == null, "next() should return null value");
        check(!it.hasNext(), "iterator should be exhausted after null value");
    }

    static void testAccessOrderNext() {
        Map<String, Integer> map = new LinkedHashMap<>(16, 0.75f, true);
        map.put("a", 1);
        map.put("b", 2);
        map.get("a");
        Iterator<Integer> it = map.values().iterator();
        check(it.next() == 2, "least recently accessed value should be first");
        check(it.next() == 1, "most recently accessed value should be last");
    }

    static void testReinsertKeepsPositionNext() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("x", 10);
        map.put("y", 20);
        map.put("x", 30);
        Iterator<Integer> it = map.values().iterator();
        check(it.next() == 30, "reinserted key should keep its insertion position");
        check(it.next() == 20, "second value should remain 20");
    }

    public static void main(String[] args) {
        testInsertionOrderNext();
        testNullValueNext();
        testAccessOrderNext();
        testReinsertKeepsPositionNext();
    }
}
