/*
 * @test
 * @summary Test the next() method of LinkedHashMap for normal and exceptional behavior
 * @run main LinkedHashMapWitupTest
 */

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testNextNormal();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    private static void testNextNormal() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("a", "alpha");
        map.put("b", "beta");
        var iterator = map.values().iterator();
        try {
            String value1 = iterator.next();
            String value2 = iterator.next();
            if (value1 == null || value2 == null) {
                throw new AssertionError("Expected non-null values");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal next()", e);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        var iterator = map.values().iterator();
        try {
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in NoSuchElementException test", e);
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("x", "ex");
        map.put("y", "why");
        var iterator = map.values().iterator();
        // Modify the map after creating iterator to trigger exception
        map.put("z", "zee");
        try {
            iterator.next();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (java.util.ConcurrentModificationException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in concurrent modification test", e);
        }
    }
}
