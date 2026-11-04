/*
 * @test
 * @summary Test for ConcurrentHashMap.retainAll(Collection<?>) method
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllWithEmptyCollection();
        testRetainAllWithMatchingElements();
        testRetainAllWithNonMatchingElements();
    }

    static void testRetainAllWithNullCollection() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        boolean exceptionThrown = false;
        try {
            map.retainAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to retainAll");
        }
    }

    static void testRetainAllWithEmptyCollection() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        Collection<String> emptyCollection = java.util.Collections.emptyList();
        boolean changed = map.retainAll(emptyCollection);
        if (changed) {
            throw new AssertionError("retainAll with empty collection should not change map");
        }
        if (map.size() != 2 || !map.containsKey("a") || !map.containsKey("b")) {
            throw new AssertionError("Map changed unexpectedly");
        }
    }

    static void testRetainAllWithMatchingElements() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        Collection<String> c = java.util.Arrays.asList("a", "b");
        boolean changed = map.retainAll(c);
        if (!changed) {
            throw new AssertionError("retainAll with matching elements should change map");
        }
        if (map.size() != 2 || !map.containsKey("a") || !map.containsKey("b")) {
            throw new AssertionError("Map contents incorrect after retainAll");
        }
    }

    static void testRetainAllWithNonMatchingElements() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        Collection<String> c = java.util.Arrays.asList("c", "d");
        boolean changed = map.retainAll(c);
        if (!changed) {
            throw new AssertionError("retainAll with non-matching elements should change map");
        }
        if (map.size() != 0) {
            throw new AssertionError("Map should be empty after retainAll with non-matching elements");
        }
    }
}
