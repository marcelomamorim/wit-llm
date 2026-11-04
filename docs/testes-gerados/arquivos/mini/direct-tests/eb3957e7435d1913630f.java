/*
 * @test
 * @summary Test ConcurrentHashMap.retainAll(Collection<?>) method behavior
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNullCollection();
        testRetainAllRemovesNonContainedElements();
        testRetainAllReturnsFalseWhenNoChange();
        testRetainAllOnEmptyMap();
        testRetainAllIteratorRemoveEffect();
    }

    static void testRetainAllWithNullCollection() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.keySet().retainAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRetainAllRemovesNonContainedElements() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("one", "1");
        map.put("two", "2");
        map.put("three", "3");

        Collection<String> retain = new ArrayList<>();
        retain.add("one");
        retain.add("three");

        boolean changed = map.keySet().retainAll(retain);

        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }

        if (map.containsKey("two")) {
            throw new RuntimeException("Key 'two' should have been removed");
        }

        if (!map.containsKey("one") || !map.containsKey("three")) {
            throw new RuntimeException("Keys 'one' and 'three' should remain");
        }
    }

    static void testRetainAllReturnsFalseWhenNoChange() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "A");
        map.put("b", "B");

        Collection<String> retain = new ArrayList<>(Arrays.asList("a", "b"));

        boolean changed = map.keySet().retainAll(retain);

        if (changed) {
            throw new RuntimeException("retainAll should return false when no elements are removed");
        }
    }

    static void testRetainAllOnEmptyMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        Collection<String> retain = new ArrayList<>();

        boolean changed = map.keySet().retainAll(retain);

        if (changed) {
            throw new RuntimeException("retainAll on empty map should return false");
        }
    }

    static void testRetainAllIteratorRemoveEffect() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("x", "X");
        map.put("y", "Y");

        Collection<String> retain = new ArrayList<>();
        retain.add("x");

        boolean changed = map.keySet().retainAll(retain);

        if (!changed) {
            throw new RuntimeException("retainAll should return true when elements are removed");
        }

        if (map.containsKey("y")) {
            throw new RuntimeException("Key 'y' should have been removed");
        }

        if (!map.containsKey("x")) {
            throw new RuntimeException("Key 'x' should remain");
        }
    }
}
