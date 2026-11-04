/*
 * @test
 * @summary Checks WeakHashMap entry iterator next() exceptions through public API
 * @run main WeakHashMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsAllEntries();
        testNextOnEmptyMapThrowsNoSuchElementException();
        testNextAfterExhaustionThrowsNoSuchElementException();
        testNextAfterStructuralModificationThrowsConcurrentModificationException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNextReturnsAllEntries() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        map.put("a", 1);
        map.put("b", 2);

        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        int count = 0;
        int sum = 0;
        while (it.hasNext()) {
            Map.Entry<String, Integer> e = it.next();
            check(e != null, "next() returned null entry");
            check(e.getKey() != null, "entry key is null");
            sum += e.getValue();
            count++;
        }
        check(count == 2, "expected 2 entries but got " + count);
        check(sum == 3, "unexpected value sum " + sum);
    }

    static void testNextOnEmptyMapThrowsNoSuchElementException() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException expected) {
            thrown = true;
        }
        check(thrown, "next() on empty map did not throw NoSuchElementException");
    }

    static void testNextAfterExhaustionThrowsNoSuchElementException() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        map.put("k", 1);
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        it.next();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException expected) {
            thrown = true;
        }
        check(thrown, "next() after exhaustion did not throw NoSuchElementException");
    }

    static void testNextAfterStructuralModificationThrowsConcurrentModificationException() {
        WeakHashMap<String, Integer> map = new WeakHashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        it.next();
        map.put("c", 3);
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException expected) {
            thrown = true;
        }
        check(thrown, "next() after structural modification did not throw ConcurrentModificationException");
    }
}
