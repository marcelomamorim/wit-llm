/*
 * @test
 * @summary Checks observable behavior of TreeMap.tailMap(K) views: range, write-through and empty cases
 * @run main TreeMapWitupTest
 */

import java.util.SortedMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        m.put(5, "e");

        SortedMap<Integer, String> tail = m.tailMap(2);
        check(tail.size() == 3, "tailMap(2) size should be 3");
        check(tail.firstKey().equals(2), "firstKey of tailMap(2) should be 2");
        check(tail.lastKey().equals(5), "lastKey of tailMap(2) should be 5");
        check(!tail.containsKey(1), "tailMap(2) must not contain key 1");

        m.put(6, "f");
        check(tail.size() == 4, "tailMap view should reflect puts on backing map");

        tail.remove(3);
        check(!m.containsKey(3), "removal through tailMap view should affect backing map");
        check(m.size() == 4, "backing map size should be 4 after view removal");

        TreeMap<Integer, String> empty = new TreeMap<>();
        check(empty.tailMap(0).isEmpty(), "tailMap on empty map should be empty");

        check(m.tailMap(100).isEmpty(), "tailMap beyond last key should be empty");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
