/*
 * @test
 * @summary Witup-generated tests for TreeMap.replace(K, V, V)
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testReplaceMatchingOldValue();
        testReplaceMismatchedOldValue();
        testReplaceAbsentKey();
        testReplaceNullOldValueMatchesNull();
        testReplaceWithNullNewValue();
        testReplaceUsesComparator();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplaceMatchingOldValue() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("k", "v1");
        check(map.replace("k", "v1", "v2"), "replace should return true when old value matches");
        check("v2".equals(map.get("k")), "value should be updated to new value");
    }

    static void testReplaceMismatchedOldValue() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("k", "v1");
        check(!map.replace("k", "v2", "v3"), "replace should return false when old value does not match");
        check("v1".equals(map.get("k")), "value should remain unchanged on mismatch");
    }

    static void testReplaceAbsentKey() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "1");
        check(!map.replace("z", null, "v"), "replace should return false for absent key");
        check(!map.containsKey("z"), "replace must not insert absent key");
        check(map.size() == 1, "map size should be unchanged");
    }

    static void testReplaceNullOldValueMatchesNull() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", null);
        check(map.replace("a", null, "x"), "null old value should match stored null");
        check("x".equals(map.get("a")), "value should be replaced after null match");
        check(!map.replace("a", null, "y"), "null old value should not match non-null stored value");
        check("x".equals(map.get("a")), "value should remain after failed null match");
    }

    static void testReplaceWithNullNewValue() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "1");
        check(map.replace("a", "1", null), "replace should accept null new value");
        check(map.get("a") == null, "stored value should be null");
        check(map.containsKey("a"), "key should still be present");
    }

    static void testReplaceUsesComparator() {
        TreeMap<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map.put("a", "1");
        check(map.replace("A", "1", "2"), "replace should locate key using comparator equivalence");
        check("2".equals(map.get("a")), "value should be updated for comparator-equivalent key");
    }
}
