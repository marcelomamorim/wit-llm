/*
 * @test
 * @summary Test for HashMap.next() method to verify normal and exceptional behavior.
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.NoSuchElementException;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testNextNormal();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextNormal() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        HashMap.KeyIterator iterator = map.new KeyIterator();
        try {
            while (iterator.hasNext()) {
                String key = iterator.next();
                System.out.println("Key: " + key);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in normal iteration", e);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        HashMap<String, String> map = new HashMap<>();
        HashMap.KeyIterator iterator = map.new KeyIterator();
        boolean exceptionThrown = false;
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        HashMap.KeyIterator iterator = map.new KeyIterator();
        // Simulate concurrent modification
        map.put("key2", "value2");
        boolean exceptionThrown = false;
        try {
            iterator.next();
        } catch (java.util.ConcurrentModificationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        }
    }
}
