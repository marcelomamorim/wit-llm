/* @test    @summary Test TreeMap.remove() method for correct behavior    @run main TreeMapWitupTest */

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
        
        // Remove the smallest key (1)
        map.remove(1);
        assert map.size() == 2 : "Size should be 2 after removal";
        assert !map.containsKey(1) : "Map should not contain key 1";
        assert map.get(2).equals("two") : "Key 2 should map to 'two'";
        assert map.get(3).equals("three") : "Key 3 should map to 'three'";
        
        // Remove a middle key (2)
        map.remove(2);
        assert map.size() == 1 : "Size should be 1 after removal";
        assert !map.containsKey(2) : "Map should not contain key 2";
        assert map.get(3).equals("three") : "Key 3 should still map to 'three'";
        
        // Remove the last key (3)
        map.remove(3);
        assert map.isEmpty() : "Map should be empty after removing all keys";
        
        // Remove from empty map should not throw exception
        map.remove(10); // should do nothing
        assert map.isEmpty() : "Map should remain empty after removing non-existent key";
    }
}
