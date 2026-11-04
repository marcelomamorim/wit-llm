/*
 * @test
 * @summary Test LinkedHashMap LinkedEntryIterator.next() for expected exceptions and normal behavior
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class LinkedHashMapWitupTest {
    public static void main(String[] args) {
        testNextNormal();
        testNextNoSuchElementException();
        testNextConcurrentModificationException();
        System.out.println("PASS");
    }

    // Test normal iteration with next() returns entries in insertion order
    private static void testNextNormal() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();

        Map.Entry<Integer, String> e1 = it.next();
        if (!e1.getKey().equals(1) || !e1.getValue().equals("one")) {
            throw new AssertionError("First entry mismatch");
        }

        Map.Entry<Integer, String> e2 = it.next();
        if (!e2.getKey().equals(2) || !e2.getValue().equals("two")) {
            throw new AssertionError("Second entry mismatch");
        }

        Map.Entry<Integer, String> e3 = it.next();
        if (!e3.getKey().equals(3) || !e3.getValue().equals("three")) {
            throw new AssertionError("Third entry mismatch");
        }
    }

    // Test that next() throws NoSuchElementException when no more elements
    private static void testNextNoSuchElementException() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "one");

        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        it.next(); // consume the only element

        boolean thrown = false;
        try {
            it.next(); // should throw NoSuchElementException
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException not thrown");
        }
    }

    // Test that next() throws ConcurrentModificationException if map modified during iteration
    private static void testNextConcurrentModificationException() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "one");
        map.put(2, "two");

        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        it.next(); // consume first element

        // Modify map structurally
        map.put(3, "three");

        boolean thrown = false;
        try {
            it.next(); // should throw ConcurrentModificationException
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        }
    }
}
