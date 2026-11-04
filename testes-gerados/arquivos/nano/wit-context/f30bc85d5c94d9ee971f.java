/* @test    @summary Test for TreeMap.next() method to verify exception paths and normal behavior    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.NoSuchElementException;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNextNormal();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextNormal() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        java.util.Iterator<Integer> iterator = map.keySet().iterator();
        try {
            while (iterator.hasNext()) {
                Integer key = iterator.next();
                System.out.println("Next key: " + key);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal iteration", e);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        TreeMap<Integer, String> map = new TreeMap<>();
        java.util.Iterator<Integer> iterator = map.keySet().iterator();
        try {
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception, expected NoSuchElementException", e);
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        java.util.Iterator<Integer> iterator = map.keySet().iterator();
        map.put(3, "three"); // modify map after iterator creation
        try {
            iterator.next();
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        } catch (java.util.ConcurrentModificationException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception, expected ConcurrentModificationException", e);
        }
    }
}
