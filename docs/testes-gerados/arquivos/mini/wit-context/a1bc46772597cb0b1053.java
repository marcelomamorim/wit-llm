/*
 * @test
 * @summary Test deterministic behavior of TreeMap.next() method
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.ConcurrentModificationException;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testNextOnEmptyMap();
        testNextAfterModification();
    }

    // Test that calling next() on an empty TreeMap iterator throws NoSuchElementException
    private static void testNextOnEmptyMap() {
        TreeMap<Integer, String> map = new TreeMap<>();
        Iterator<Integer> it = map.keySet().iterator();
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

    // Test that modifying the TreeMap after creating an iterator causes ConcurrentModificationException on next()
    private static void testNextAfterModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        Iterator<Integer> it = map.keySet().iterator();
        // Advance iterator once to avoid NoSuchElementException
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        Integer first = it.next();
        // Modify map structurally
        map.put(3, "three");
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ConcurrentModificationException after modifying map during iteration");
        }
    }
}
