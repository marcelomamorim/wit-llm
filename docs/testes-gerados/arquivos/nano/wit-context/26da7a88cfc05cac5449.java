/* @test    @summary Test for TreeMap.next() method to verify exception paths and behavior    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.NoSuchElementException;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNextThrowsNoSuchElementException();
        testNextBehavior();
    }

    private static void testNextThrowsNoSuchElementException() {
        TreeMap<Integer, String> map = new TreeMap<>();
        // Create an iterator with no elements
        java.util.Iterator<Integer> iterator = map.values().iterator();
        // Move iterator to end
        while (iterator.hasNext()) {
            iterator.next();
        }
        // Now calling next() should throw NoSuchElementException
        try {
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    private static void testNextBehavior() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        java.util.Iterator<String> iterator = map.values().iterator();
        if (!iterator.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        String value = iterator.next();
        if (!"one".equals(value)) {
            throw new AssertionError("Expected value 'one', got: " + value);
        }
        // Call next() again
        value = iterator.next();
        if (!"two".equals(value)) {
            throw new AssertionError("Expected value 'two', got: " + value);
        }
    }
}
