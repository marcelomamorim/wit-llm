/*
 * @test
 * @summary Test for ConcurrentSkipListMap.descendingKeySet() method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableSet;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testDescendingKeySetReturnsNavigableSet();
        testDescendingKeySetEmptyMap();
        testDescendingKeySetWithElements();
    }

    private static void testDescendingKeySetReturnsNavigableSet() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        NavigableSet<Integer> keySet = map.descendingKeySet();
        if (keySet == null) {
            throw new AssertionError("descendingKeySet() returned null");
        }
        if (!(keySet instanceof NavigableSet)) {
            throw new AssertionError("descendingKeySet() did not return a NavigableSet");
        }
    }

    private static void testDescendingKeySetEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        NavigableSet<Integer> keySet = map.descendingKeySet();
        if (!keySet.isEmpty()) {
            throw new AssertionError("descendingKeySet() on empty map should be empty");
        }
        if (keySet.size() != 0) {
            throw new AssertionError("descendingKeySet() on empty map should have size 0");
        }
    }

    private static void testDescendingKeySetWithElements() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(2, "two");
        NavigableSet<Integer> keySet = map.descendingKeySet();
        if (keySet.size() != 3) {
            throw new AssertionError("Expected size 3, got " + keySet.size());
        }
        Integer[] expectedOrder = {3, 2, 1};
        int index = 0;
        for (Integer key : keySet) {
            if (!key.equals(expectedOrder[index])) {
                throw new AssertionError("Expected key " + expectedOrder[index] + " but got " + key);
            }
            index++;
        }
        // Test that the returned set is indeed a NavigableSet and supports navigation
        Integer first = keySet.first();
        Integer last = keySet.last();
        if (!first.equals(3) || !last.equals(1)) {
            throw new AssertionError("Unexpected first or last element: first=" + first + ", last=" + last);
        }
        // Test descendingSet view
        NavigableSet<Integer> descendingView = keySet.descendingSet();
        Integer[] expectedDescendingOrder = {1, 2, 3};
        index = 0;
        for (Integer key : descendingView) {
            if (!key.equals(expectedDescendingOrder[index])) {
                throw new AssertionError("Descending view mismatch at index " + index + ": expected " + expectedDescendingOrder[index] + " but got " + key);
            }
            index++;
        }
    }
}
