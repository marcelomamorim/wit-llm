/*
 * @test
 * @summary WITUP-derived behavioral tests for LinkedHashMap values iterator next()
 * @run main LinkedHashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsValuesInInsertionOrder();
        testNextThrowsNoSuchElementWhenExhausted();
        testNextThrowsNoSuchElementOnEmptyMap();
        testNextThrowsConcurrentModificationAfterStructuralChange();
        System.out.println("PASSED");
    }

    static void testNextReturnsValuesInInsertionOrder() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Iterator<Integer> it = map.values().iterator();
        check(it.hasNext(), "hasNext should be true for first element");
        check(it.next() == 1, "first next() should return 1");
        check(it.next() == 2, "second next() should return 2");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testNextThrowsNoSuchElementWhenExhausted() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        Iterator<Integer> it = map.values().iterator();
        check(it.next() == 1, "first next() should return 1");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() past the end should throw NoSuchElementException");
    }

    static void testNextThrowsNoSuchElementOnEmptyMap() {
        Map<String, Integer> map = new LinkedHashMap<>();
        Iterator<Integer> it = map.values().iterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty map should throw NoSuchElementException");
    }

    static void testNextThrowsConcurrentModificationAfterStructuralChange() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Iterator<Integer> it = map.values().iterator();
        check(it.next() == 1, "first next() should return 1");
        map.put("c", 3);
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "next() after structural modification should throw ConcurrentModificationException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
