/*
 * @test
 * @summary Verify HashMap entry iterator next() returns entries, throws NoSuchElementException when exhausted, and ConcurrentModificationException after a structural modification
 * @run main HashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testReturnsEntryAndThenExhausts();
        testNullKeyAndValueEntry();
        testConcurrentModificationOnNext();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void fail(String message) {
        throw new RuntimeException(message);
    }

    static void testReturnsEntryAndThenExhausts() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        check(it.hasNext(), "iterator should have one element");
        Map.Entry<String, Integer> entry = it.next();
        check("a".equals(entry.getKey()), "unexpected key");
        check(Integer.valueOf(1).equals(entry.getValue()), "unexpected value");
        check(!it.hasNext(), "iterator should be exhausted");
        try {
            it.next();
            fail("next() on exhausted iterator should throw NoSuchElementException");
        } catch (NoSuchElementException expected) {
        }
    }

    static void testNullKeyAndValueEntry() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put(null, null);
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        Map.Entry<String, Integer> entry = it.next();
        check(entry.getKey() == null, "null key should be returned");
        check(entry.getValue() == null, "null value should be returned");
        check(!it.hasNext(), "iterator should be exhausted after single entry");
    }

    static void testConcurrentModificationOnNext() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        it.next();
        map.put("c", 3);
        try {
            it.next();
            fail("next() after structural modification should throw ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }
}
