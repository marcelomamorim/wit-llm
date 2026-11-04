/*
 * @test
 * @summary Test ConcurrentSkipListMap descendingKeySet method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableSet;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testDescendingKeySetEmptyMap();
        testDescendingKeySetNaturalOrder();
        testDescendingKeySetCustomComparator();
        testDescendingKeySetSubMapOrderException();
    }

    static void testDescendingKeySetEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        NavigableSet<Integer> descendingKeys = map.descendingKeySet();
        if (!descendingKeys.isEmpty()) {
            throw new AssertionError("descendingKeySet of empty map should be empty");
        }
    }

    static void testDescendingKeySetNaturalOrder() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(2, "two");
        NavigableSet<Integer> descendingKeys = map.descendingKeySet();
        Integer prev = null;
        for (Integer key : descendingKeys) {
            if (prev != null && key >= prev) {
                throw new AssertionError("Keys not in descending order");
            }
            prev = key;
        }
    }

    static void testDescendingKeySetCustomComparator() {
        Comparator<String> reverseCmp = (a, b) -> b.compareTo(a);
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>(reverseCmp);
        map.put("a", 1);
        map.put("c", 3);
        map.put("b", 2);
        NavigableSet<String> descendingKeys = map.descendingKeySet();
        String prev = null;
        for (String key : descendingKeys) {
            if (prev != null && key.compareTo(prev) <= 0) {
                throw new AssertionError("Keys not in descending order for custom comparator");
            }
            prev = key;
        }
    }

    static void testDescendingKeySetSubMapOrderException() {
        // This test tries to provoke the IllegalArgumentException described in WIT expath
        // by creating a SubMap with lo > hi according to comparator.
        // Since SubMap is package-private, we test via subMap with reversed bounds.
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        try {
            // subMap with fromKey > toKey should throw IllegalArgumentException
            map.subMap(3, true, 1, true).descendingKeySet();
            throw new AssertionError("Expected IllegalArgumentException for subMap with fromKey > toKey");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}