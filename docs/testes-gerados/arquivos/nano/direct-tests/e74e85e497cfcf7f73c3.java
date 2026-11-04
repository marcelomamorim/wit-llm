/* @test    @summary Test TreeMap.remove() method for expected behavior and no side effects    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveMethod();
    }

    public static void testRemoveMethod() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        // Remove existing key
        map.remove(2);
        assert map.size() == 2 : "Size should be 2 after removal";
        assert !map.containsKey(2) : "Map should not contain key 2 after removal";
        assert map.get(1).equals("one") : "Key 1 should still be present";
        assert map.get(3).equals("three") : "Key 3 should still be present";

        // Remove non-existing key (should do nothing)
        map.remove(99);
        assert map.size() == 2 : "Size should remain 2 after removing non-existing key";

        // Remove remaining keys
        map.remove(1);
        map.remove(3);
        assert map.isEmpty() : "Map should be empty after removing all keys";
    }
}
