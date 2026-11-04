/*
 * @test
 * @summary Test TreeMap remove() method calls removeAscending() without error
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

        Iterator<Integer> it = map.keySet().iterator();
        if (it.hasNext()) {
            it.next();
            it.remove(); // should call TreeMap$PrivateEntryIterator.remove internally
        }

        // After removal, map size should be 2
        if (map.size() != 2) {
            throw new RuntimeException("Remove did not decrease size as expected");
        }

        // Remove remaining keys via iterator remove
        while (it.hasNext()) {
            it.next();
            it.remove();
        }

        if (!map.isEmpty()) {
            throw new RuntimeException("Map should be empty after removing all elements");
        }
    }
}
