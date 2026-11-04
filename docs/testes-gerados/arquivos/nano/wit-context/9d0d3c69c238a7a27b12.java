/*
 * @test
 * @summary Tests for java.util.TreeMap.replace(K, V, V)
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testReplaceWithExistingKeyAndMatchingOldValue();
        testReplaceWithExistingKeyAndNonMatchingOldValue();
        testReplaceWithNonExistingKey();
    }

    private static void testReplaceWithExistingKeyAndMatchingOldValue() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "alpha");
        boolean result = map.replace("a", "alpha", "beta");
        if (!result) {
            throw new AssertionError("Expected replace to return true for existing key with matching old value");
        }
        if (!"beta".equals(map.get("a"))) {
            throw new AssertionError("Expected value to be updated to 'beta'");
        }
    }

    private static void testReplaceWithExistingKeyAndNonMatchingOldValue() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("b", "beta");
        boolean result = map.replace("b", "alpha", "gamma");
        if (result) {
            throw new AssertionError("Expected replace to return false for existing key with non-matching old value");
        }
        if (!"beta".equals(map.get("b"))) {
            throw new AssertionError("Expected value to remain 'beta'");
        }
    }

    private static void testReplaceWithNonExistingKey() {
        TreeMap<String, String> map = new TreeMap<>();
        boolean result = map.replace("c", "delta", "epsilon");
        if (result) {
            throw new AssertionError("Expected replace to return false for non-existing key");
        }
        if (map.containsKey("c")) {
            throw new AssertionError("Map should not contain key 'c'");
        }
    }
}
