/*
 * @test
 * @summary Test for WeakHashMap.next() method to observe behavior and exception handling.
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testNextMethodNormal();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextMethodNormal() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        WeakHashMap.KeyIterator<String, String> iterator = map.new KeyIterator();
        try {
            String key = iterator.next();
            System.out.println("Normal next() returned key: " + key);
        } catch (Exception e) {
            System.err.println("Unexpected exception in normal next(): " + e);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        WeakHashMap.KeyIterator<String, String> iterator = map.new KeyIterator();
        try {
            iterator.next();
            System.err.println("Expected NoSuchElementException was not thrown.");
        } catch (java.util.NoSuchElementException e) {
            System.out.println("Caught expected NoSuchElementException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        WeakHashMap<String, String> map = new WeakHashMap<>();
        map.put("key1", "value1");
        WeakHashMap.KeyIterator<String, String> iterator = map.new KeyIterator();
        // Modify the map to cause concurrent modification
        map.put("key2", "value2");
        try {
            iterator.next();
            System.err.println("Expected ConcurrentModificationException was not thrown.");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Caught expected ConcurrentModificationException.");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
