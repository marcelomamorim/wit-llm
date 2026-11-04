/*
 * @test
 * @summary Test for HashMap.next() method to observe behavior and exception paths.
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.NoSuchElementException;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsValue();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextReturnsValue() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        HashMap<String, String>.ValueIterator iterator = map.new ValueIterator();
        // Advance iterator to a valid position
        iterator.next();
        String value = iterator.next();
        if (value == null) {
            throw new AssertionError("Expected non-null value from next()");
        }
        System.out.println("testNextReturnsValue passed.");
    }

    private static void testNextThrowsNoSuchElementException() {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String>.ValueIterator iterator = map.new ValueIterator();
        try {
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException was not thrown.");
        } catch (NoSuchElementException e) {
            System.out.println("testNextThrowsNoSuchElementException passed.");
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        HashMap<String, String>.ValueIterator iterator = map.new ValueIterator();
        // Simulate concurrent modification
        map.put("key2", "value2");
        // Manually set modCount to simulate concurrent modification detection
        // Note: This is a simplified simulation; actual modCount manipulation may require reflection or internal access.
        // For the purpose of this test, we assume that calling next() after modification throws the exception.
        try {
            iterator.next();
            throw new AssertionError("Expected ConcurrentModificationException was not thrown.");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("testNextThrowsConcurrentModificationException passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
