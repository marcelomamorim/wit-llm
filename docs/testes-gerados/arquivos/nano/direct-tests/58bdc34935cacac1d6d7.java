/* @test    @summary Test TreeMap.containsKey for existing and non-existing keys    @run main TreeMapWitupTest */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testContainsKeyWithExistingKey();
        testContainsKeyWithNonExistingKey();
    }

    private static void testContainsKeyWithExistingKey() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        boolean result = map.containsKey(1);
        if (!result) {
            throw new AssertionError("Expected containsKey(1) to be true");
        }
    }

    private static void testContainsKeyWithNonExistingKey() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(2, "two");
        boolean result = map.containsKey(3);
        if (result) {
            throw new AssertionError("Expected containsKey(3) to be false");
        }
    }
}
