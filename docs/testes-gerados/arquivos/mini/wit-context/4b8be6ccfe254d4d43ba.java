/*
 * @test
 * @summary Test TreeMap.EntryIterator.next() for expected exceptions and normal behavior
 * @run main TreeMapWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.Iterator;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
        testNextReturnsEntry();
    }

    // Test that next() throws NoSuchElementException when no next element
    private static void testNextThrowsNoSuchElementException() {
        TreeMap<Integer, String> map = new TreeMap<>();
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty iterator");
        }
    }

    // Test that next() throws ConcurrentModificationException if map modified after iterator creation
    private static void testNextThrowsConcurrentModificationException() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        map.put(2, "two"); // structural modification after iterator creation
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ConcurrentModificationException when calling next() after map modification");
        }
    }

    // Test that next() returns the correct entry when iterator is valid
    private static void testNextReturnsEntry() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
        Map.Entry<Integer, String> e1 = it.next();
        if (e1 == null || !e1.getKey().equals(10) || !e1.getValue().equals("ten")) {
            throw new AssertionError("First entry incorrect");
        }
        Map.Entry<Integer, String> e2 = it.next();
        if (e2 == null || !e2.getKey().equals(20) || !e2.getValue().equals("twenty")) {
            throw new AssertionError("Second entry incorrect");
        }
    }
}
