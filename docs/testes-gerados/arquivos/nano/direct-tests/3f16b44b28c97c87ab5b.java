/* @test    @summary Test TreeMap.get() with existing and non-existing keys    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testGetExistingKey();
        testGetNonExistingKey();
    }

    private static void testGetExistingKey() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        Integer value = map.get("b");
        if (value == null || value != 2) {
            throw new AssertionError("Expected value 2 for key 'b', but got: " + value);
        }
    }

    private static void testGetNonExistingKey() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("x", 10);
        map.put("y", 20);
        Integer value = map.get("z");
        if (value != null) {
            throw new AssertionError("Expected null for non-existing key 'z', but got: " + value);
        }
    }
}
