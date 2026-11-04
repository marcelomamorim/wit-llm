/* @test    @summary Test for TreeMap.replace(K, V) method to verify correct behavior and regressions    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.Map;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testReplaceExistingKey();
        testReplaceNonExistingKey();
    }

    private static void testReplaceExistingKey() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        Integer oldValue = map.replace("a", 42);
        if (oldValue == null) {
            throw new AssertionError("Expected old value, but got null");
        }
        if (oldValue != 1) {
            throw new AssertionError("Expected old value 1, but got " + oldValue);
        }
        Integer newValue = map.get("a");
        if (newValue == null || newValue != 42) {
            throw new AssertionError("Expected new value 42, but got " + newValue);
        }
    }

    private static void testReplaceNonExistingKey() {
        TreeMap<String, Integer> map = new TreeMap<>();
        Integer result = map.replace("b", 100);
        if (result != null) {
            throw new AssertionError("Expected null for non-existing key, but got " + result);
        }
        if (map.containsKey("b")) {
            throw new AssertionError("Map should not contain key 'b' after replace");
        }
    }
}
