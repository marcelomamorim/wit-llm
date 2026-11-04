/*
 * @test
 * @summary Test LinkedHashMap LinkedValueIterator.next() for expected exceptions and normal behavior
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testNextNormal();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextNormal() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "one");
        map.put(2, "two");

        Iterator<String> it = map.values().iterator();

        // Should return values in insertion order
        String first = it.next();
        if (!"one".equals(first)) {
            throw new AssertionError("Expected first value 'one', got: " + first);
        }
        String second = it.next();
        if (!"two".equals(second)) {
            throw new AssertionError("Expected second value 'two', got: " + second);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "one");

        Iterator<String> it = map.values().iterator();
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

    private static void testNextThrowsConcurrentModificationException() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "one");

        Iterator<String> it = map.values().iterator();

        // Modify map after iterator creation to cause ConcurrentModificationException
        map.put(2, "two");

        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ConcurrentModificationException when map modified after iterator creation");
        }
    }
}
