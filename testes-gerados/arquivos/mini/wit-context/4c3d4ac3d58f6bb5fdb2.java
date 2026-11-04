/*
 * @test
 * @summary Test java.util.HashMap.next() method for expected exceptions and normal behavior
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class HashMapWitupTest {
    public static void main(String[] args) {
        testNextNormalAndExceptions();
    }

    private static void testNextNormalAndExceptions() {
        // Create a HashMap and populate it
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        // Get iterator over entrySet
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();

        // Test normal iteration: next() returns entries until exhausted
        Map.Entry<String, String> e1 = it.next();
        if (e1 == null) {
            throw new AssertionError("next() returned null on first call");
        }
        Map.Entry<String, String> e2 = it.next();
        if (e2 == null) {
            throw new AssertionError("next() returned null on second call");
        }

        // After two next() calls, iterator should be exhausted
        boolean noSuchElementThrown = false;
        try {
            it.next();
        } catch (NoSuchElementException ex) {
            noSuchElementThrown = true;
        }
        if (!noSuchElementThrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on exhausted iterator");
        }

        // Test ConcurrentModificationException by modifying map after iterator creation
        Iterator<Map.Entry<String, String>> it2 = map.entrySet().iterator();
        // Modify map to cause modCount != expectedModCount
        map.put("key3", "value3");
        boolean concurrentModificationThrown = false;
        try {
            it2.next();
        } catch (ConcurrentModificationException ex) {
            concurrentModificationThrown = true;
        }
        if (!concurrentModificationThrown) {
            throw new AssertionError("Expected ConcurrentModificationException after map modification and next() call");
        }
    }
}
