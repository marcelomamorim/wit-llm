/*
 * @test
 * @summary Test HashMap.ValueIterator.next() for expected exceptions and normal behavior
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testNextNormal();
        testNextNoSuchElementException();
        testNextConcurrentModificationException();
    }

    private static void testNextNormal() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        Iterator<String> it = map.values().iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next element");
        }
        String val = it.next();
        if (!"value1".equals(val)) {
            throw new AssertionError("Expected value1 but got " + val);
        }
    }

    private static void testNextNoSuchElementException() {
        HashMap<String, String> map = new HashMap<>();
        Iterator<String> it = map.values().iterator();
        if (it.hasNext()) {
            throw new AssertionError("Iterator should have no next element");
        }
        try {
            it.next();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    private static void testNextConcurrentModificationException() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        Iterator<String> it = map.values().iterator();
        map.put("key2", "value2"); // structural modification after iterator creation
        try {
            it.next();
            throw new AssertionError("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
