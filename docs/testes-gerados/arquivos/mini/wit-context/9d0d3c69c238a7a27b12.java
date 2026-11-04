/*
 * @test
 * @summary Test java.util.TreeMap.replace(K key, V oldValue, V newValue) method
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Objects;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testReplaceReturnsTrueWhenOldValueMatches();
        testReplaceReturnsFalseWhenKeyAbsent();
        testReplaceReturnsFalseWhenOldValueDoesNotMatch();
    }

    private static void testReplaceReturnsTrueWhenOldValueMatches() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("key1", "value1");
        boolean replaced = map.replace("key1", "value1", "value2");
        if (!replaced) {
            throw new AssertionError("Expected replace to return true when oldValue matches current value");
        }
        String val = map.get("key1");
        if (!Objects.equals(val, "value2")) {
            throw new AssertionError("Expected value to be updated to newValue after replace");
        }
    }

    private static void testReplaceReturnsFalseWhenKeyAbsent() {
        TreeMap<String, String> map = new TreeMap<>();
        boolean replaced = map.replace("absentKey", "anyOldValue", "newValue");
        if (replaced) {
            throw new AssertionError("Expected replace to return false when key is absent");
        }
    }

    private static void testReplaceReturnsFalseWhenOldValueDoesNotMatch() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("key2", "value1");
        boolean replaced = map.replace("key2", "wrongOldValue", "value2");
        if (replaced) {
            throw new AssertionError("Expected replace to return false when oldValue does not match current value");
        }
        String val = map.get("key2");
        if (!Objects.equals(val, "value1")) {
            throw new AssertionError("Expected value to remain unchanged when oldValue does not match");
        }
    }
}
