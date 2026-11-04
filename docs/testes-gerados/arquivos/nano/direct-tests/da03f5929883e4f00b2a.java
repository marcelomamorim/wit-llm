/*
 * @test
 * @summary Test descendingKeySet() method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableSet;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testDescendingKeySetReturnsNonNullSet();
        testDescendingKeySetReturnsCorrectType();
        testDescendingKeySetWithElements();
        testDescendingKeySetEmpty();
    }

    static void testDescendingKeySetReturnsNonNullSet() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        NavigableSet<Integer> keySet = map.descendingKeySet();
        if (keySet == null) {
            throw new AssertionError("descendingKeySet() returned null");
        }
    }

    static void testDescendingKeySetReturnsCorrectType() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        NavigableSet<Integer> keySet = map.descendingKeySet();
        if (!(keySet instanceof java.util.NavigableSet)) {
            throw new AssertionError("descendingKeySet() does not return a NavigableSet");
        }
    }

    static void testDescendingKeySetWithElements() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        NavigableSet<Integer> keySet = map.descendingKeySet();
        Integer firstKey = keySet.first();
        Integer lastKey = keySet.last();
        if (firstKey != 3 || lastKey != 1) {
            throw new AssertionError("descendingKeySet() elements are not in expected order");
        }
        if (!keySet.contains(2)) {
            throw new AssertionError("descendingKeySet() does not contain expected key 2");
        }
        // Test iteration order
        Iterator<Integer> it = keySet.iterator();
        if (!it.hasNext() || it.next() != 3) {
            throw new AssertionError("Iterator does not start with highest key");
        }
        if (!it.hasNext() || it.next() != 2) {
            throw new AssertionError("Iterator does not follow descending order");
        }
        if (!it.hasNext() || it.next() != 1) {
            throw new AssertionError("Iterator does not follow descending order");
        }
    }

    static void testDescendingKeySetEmpty() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        NavigableSet<Integer> keySet = map.descendingKeySet();
        if (keySet.isEmpty() == false) {
            throw new AssertionError("descendingKeySet() should be empty for empty map");
        }
        if (keySet.size() != 0) {
            throw new AssertionError("descendingKeySet() size should be 0 for empty map");
        }
        try {
            keySet.first();
            throw new AssertionError("Expected NoSuchElementException for first() on empty set");
        } catch (NoSuchElementException e) {
            // expected
        }
        try {
            keySet.last();
            throw new AssertionError("Expected NoSuchElementException for last() on empty set");
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
