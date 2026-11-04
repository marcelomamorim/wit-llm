/*
 * @test
 * @summary Test TreeMap remove() method behavior
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Iterator;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testRemoveFromIterator();
        testRemoveWithoutNext();
    }

    private static void testRemoveFromIterator() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        Iterator<Integer> it = map.keySet().iterator();
        if (it.hasNext()) {
            it.next();
            it.remove(); // should remove the first entry
        }

        // After removal, size should be 2
        if (map.size() != 2) {
            throw new RuntimeException("Failed: size after remove() expected 2 but was " + map.size());
        }

        // The key 1 should no longer be present
        if (map.containsKey(1)) {
            throw new RuntimeException("Failed: key 1 should have been removed");
        }
    }

    private static void testRemoveWithoutNext() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");

        Iterator<Integer> it = map.keySet().iterator();

        boolean threw = false;
        try {
            it.remove(); // IllegalStateException expected
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Failed: remove() without next() should throw IllegalStateException");
        }
    }
}
