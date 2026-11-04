/* @test    @summary Test for LinkedHashMap.next() method to verify normal and exceptional behavior    @run main LinkedHashMapWitupTest */

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

public class LinkedHashMapWitupTest {

    public static void main(String[] args) {
        testNextNormal();
        testNextNoSuchElementException();
        testNextConcurrentModificationException();
    }

    private static void testNextNormal() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("a", "alpha");
        map.put("b", "beta");
        map.put("c", "gamma");
        var iterator = map.keySet().iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key.equals("a")) {
                if (!map.get(key).equals("alpha")) {
                    throw new AssertionError("Unexpected value for key 'a'");
                }
                found = true;
            }
        }
        if (!found) {
            throw new AssertionError("Key 'a' not found in iteration");
        }
    }

    private static void testNextNoSuchElementException() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("x", "ex");
        var iterator = map.keySet().iterator();
        iterator.next(); // first element
        try {
            iterator.next(); // should throw NoSuchElementException
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    private static void testNextConcurrentModificationException() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        var iterator = map.keySet().iterator();
        map.put("k3", "v3"); // modify after iterator creation
        try {
            iterator.next(); // should throw ConcurrentModificationException
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (java.util.ConcurrentModificationException e) {
            // expected
        }
    }
}
