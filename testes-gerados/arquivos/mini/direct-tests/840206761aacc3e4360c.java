/*
 * @test
 * @summary Test ConcurrentSkipListMap removeIf(Predicate) method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.Iterator;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfEmptyMap();
        testRemoveIfRemoveSome();
        testRemoveIfRemoveNone();
        testRemoveIfRemoveAll();
    }

    static void testRemoveIfNullPredicate() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemoveIfEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        boolean removed = map.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        });
        if (removed) {
            throw new RuntimeException("removeIf on empty map should return false");
        }
        if (!map.isEmpty()) {
            throw new RuntimeException("Map should remain empty after removeIf");
        }
    }

    static void testRemoveIfRemoveSome() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        boolean removed = map.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return s.length() == 3; // remove "one" and "two"
            }
        });

        if (!removed) {
            throw new RuntimeException("Expected some elements to be removed");
        }
        if (map.containsKey(1) || map.containsKey(2)) {
            throw new RuntimeException("Keys 1 and 2 should have been removed");
        }
        if (!map.containsKey(3)) {
            throw new RuntimeException("Key 3 should remain");
        }
    }

    static void testRemoveIfRemoveNone() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");

        boolean removed = map.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return false; // remove none
            }
        });

        if (removed) {
            throw new RuntimeException("Expected no elements to be removed");
        }
        if (map.size() != 2) {
            throw new RuntimeException("Map size should remain 2");
        }
    }

    static void testRemoveIfRemoveAll() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");

        boolean removed = map.removeIf(new Predicate<String>() {
            public boolean test(String s) {
                return true; // remove all
            }
        });

        if (!removed) {
            throw new RuntimeException("Expected all elements to be removed");
        }
        if (!map.isEmpty()) {
            throw new RuntimeException("Map should be empty after removeIf");
        }
    }
}
