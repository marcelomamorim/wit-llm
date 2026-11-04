/*
 * @test
 * @summary Test java.util.TreeMap next() method behavior including exception paths
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsKeysInOrder();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextReturnsKeysInOrder() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        Iterator<Integer> it = map.keySet().iterator();

        int expected = 1;
        while (it.hasNext()) {
            Integer key = it.next();
            if (key != expected) {
                throw new AssertionError("Expected key " + expected + " but got " + key);
            }
            expected++;
        }
        if (expected != 4) {
            throw new AssertionError("Iterator did not return all keys");
        }
    }

    private static void testNextThrowsNoSuchElementException() {
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

    private static void testNextThrowsConcurrentModificationException() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        Iterator<Integer> it = map.keySet().iterator();
        // Modify map structurally after iterator creation
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
