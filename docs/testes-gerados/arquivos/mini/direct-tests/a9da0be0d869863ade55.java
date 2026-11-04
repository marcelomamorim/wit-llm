/*
 * @test
 * @summary Test TreeMap remove() method calls removeDescending() without error
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Iterator;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        Iterator<Integer> it = map.descendingKeySet().iterator();
        if (it.hasNext()) {
            it.next();
            it.remove(); // This calls remove() on the iterator, which should call removeDescending()
        }

        // After removal, size should be 2
        if (map.size() != 2) {
            throw new AssertionError("Expected size 2 after remove, got " + map.size());
        }

        // The removed key should no longer be present
        if (map.containsKey(3)) {
            throw new AssertionError("Key 3 should have been removed");
        }

        // Remove remaining keys using descending iterator remove
        it = map.descendingKeySet().iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }

        if (!map.isEmpty()) {
            throw new AssertionError("Map should be empty after removing all elements");
        }
    }
}
