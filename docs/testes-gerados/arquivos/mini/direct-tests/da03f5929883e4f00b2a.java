/*
 * @test
 * @summary Test ConcurrentSkipListMap descendingKeySet method
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableSet;
import java.util.Iterator;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Test empty map descendingKeySet
        NavigableSet<Integer> descKeysEmpty = map.descendingKeySet();
        if (!descKeysEmpty.isEmpty()) {
            throw new AssertionError("descendingKeySet of empty map should be empty");
        }

        // Add some entries
        map.put(1, "one");
        map.put(3, "three");
        map.put(2, "two");

        NavigableSet<Integer> descKeys = map.descendingKeySet();

        // Check size
        if (descKeys.size() != 3) {
            throw new AssertionError("descendingKeySet size expected 3 but was " + descKeys.size());
        }

        // Check order is descending
        Iterator<Integer> it = descKeys.iterator();
        int prev = Integer.MAX_VALUE;
        while (it.hasNext()) {
            int current = it.next();
            if (current >= prev) {
                throw new AssertionError("descendingKeySet not in descending order: " + current + " >= " + prev);
            }
            prev = current;
        }

        // Check that descendingKeySet reflects map changes
        map.remove(2);
        if (descKeys.size() != 2) {
            throw new AssertionError("descendingKeySet size after removal expected 2 but was " + descKeys.size());
        }

        // Check contains
        if (!descKeys.contains(3) || !descKeys.contains(1)) {
            throw new AssertionError("descendingKeySet missing expected keys after removal");
        }

        // Check that descendingKeySet is a view: add via map and check
        map.put(4, "four");
        if (!descKeys.contains(4)) {
            throw new AssertionError("descendingKeySet should reflect new key 4");
        }

        System.out.println("Test passed.");
    }
}
