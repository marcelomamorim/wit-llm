/* @test    @summary Test for LinkedHashMap.next() method to verify normal and exceptional behavior    @run main LinkedHashMapWitupTest */

import java.util.LinkedHashMap;
import java.util.Map;
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
        map.put("c", "gamma");
        Map.Entry<String, String> iterator = map.entrySet().iterator().next();
        // Advance iterator to the first element
        for (int i = 0; i < 2; i++) {
            iterator = map.entrySet().iterator().next();
        }
        try {
            Map.Entry<String, String> result = iterator;
            if (result == null) {
                throw new AssertionError("Expected non-null result");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal next() call", e);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("x", "xi");
        // Create an iterator and exhaust it
        java.util.Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            it.next();
        }
        // Now calling next() again should throw NoSuchElementException
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testNextThrowsConcurrentModificationException() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("y", "upsilon");
        java.util.Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        map.put("z", "zeta"); // modify map after iterator creation
        try {
            it.next();
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        } catch (java.util.ConcurrentModificationException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
