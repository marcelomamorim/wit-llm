/*
 * @test
 * @summary Test LinkedHashMap LinkedKeyIterator.next() for expected exceptions and normal behavior
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testNextNormal();
        testNextNoSuchElementException();
        testNextConcurrentModificationException();
    }

    private static void testNextNormal() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("one", "1");
        map.put("two", "2");

        Iterator<String> it = map.keySet().iterator();
        // next() should return keys in insertion order
        String first = it.next();
        if (!"one".equals(first)) {
            throw new AssertionError("Expected first key 'one', got: " + first);
        }
        String second = it.next();
        if (!"two".equals(second)) {
            throw new AssertionError("Expected second key 'two', got: " + second);
        }
    }

    private static void testNextNoSuchElementException() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("only", "1");

        Iterator<String> it = map.keySet().iterator();
        it.next(); // consume the only element
        boolean thrown = false;
        try {
            it.next(); // should throw NoSuchElementException
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() with no more elements");
        }
    }

    private static void testNextConcurrentModificationException() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        Iterator<String> it = map.keySet().iterator();
        it.next(); // consume first element
        // Modify map structurally after iterator creation
        map.put("c", "3");

        boolean thrown = false;
        try {
            it.next(); // should throw ConcurrentModificationException
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ConcurrentModificationException after structural modification during iteration");
        }
    }
}
