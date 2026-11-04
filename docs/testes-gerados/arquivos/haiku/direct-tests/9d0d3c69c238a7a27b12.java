/*
 * @test
 * @summary Behavioral tests for TreeMap.replace(K, V, V)
 * @run main TreeMapWitupTest
 */

import java.util.Map;
import java.util.TreeMap;

public class TreeMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReplaceWithMatchingOldValueReturnsTrueAndUpdates() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "1");
        boolean result = map.replace("a", "1", "2");
        check(result, "replace with matching old value should return true");
        check("2".equals(map.get("a")), "value should be updated to new value");
        check(map.size() == 1, "size should remain unchanged");
    }

    static void testReplaceWithMismatchedOldValueReturnsFalse() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "1");
        boolean result = map.replace("a", "X", "2");
        check(!result, "replace with mismatched old value should return false");
        check("1".equals(map.get("a")), "value should remain unchanged");
    }

    static void testReplaceAbsentKeyReturnsFalseAndDoesNotInsert() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "1");
        boolean result = map.replace("b", "1", "2");
        check(!result, "replace on absent key should return false");
        check(!map.containsKey("b"), "absent key should not be inserted");
        check(map.size() == 1, "size should remain unchanged");
    }

    static void testReplaceOnEmptyMapReturnsFalse() {
        TreeMap<String, String> map = new TreeMap<>();
        boolean result = map.replace("a", null, "2");
        check(!result, "replace on empty map should return false");
        check(map.isEmpty(), "empty map should remain empty");
    }

    static void testNullOldValueMatchesNullMapping() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", null);
        boolean result = map.replace("a", null, "x");
        check(result, "null old value should match null mapping");
        check("x".equals(map.get("a")), "value should be replaced");
    }

    static void testNullOldValueDoesNotMatchNonNullMapping() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "1");
        boolean result = map.replace("a", null, "x");
        check(!result, "null old value should not match non-null mapping");
        check("1".equals(map.get("a")), "value should remain unchanged");
    }

    static void testNonNullOldValueDoesNotMatchNullMapping() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", null);
        boolean result = map.replace("a", "1", "x");
        check(!result, "non-null old value should not match null mapping");
        check(map.get("a") == null, "value should remain null");
    }

    static void testReplaceWithNullNewValueStoresNull() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "1");
        boolean result = map.replace("a", "1", null);
        check(result, "replace should return true");
        check(map.containsKey("a"), "key should still be present");
        check(map.get("a") == null, "value should be null");
    }

    static void testComparatorBasedKeyMatching() {
        TreeMap<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map.put("a", "1");
        boolean result = map.replace("A", "1", "2");
        check(result, "comparator should treat \"A\" as equal to \"a\"");
        check("2".equals(map.get("a")), "value should be updated through comparator-equal key");
        check(map.size() == 1, "size should remain unchanged");
    }

    static void testNullKeyWithNaturalOrderingThrowsNpe() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "1");
        boolean thrown = false;
        try {
            map.replace(null, "1", "2");
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null key with natural ordering should throw NullPointerException");
    }

    static void testSortedMapViewStillReflectsReplace() {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("k", 1);
        Map<String, Integer> view = map;
        check(view.replace("k", 1, 5), "replace through Map reference should return true");
        check(map.get("k") == 5, "underlying map should reflect the replacement");
    }

    public static void main(String[] args) {
        testReplaceWithMatchingOldValueReturnsTrueAndUpdates();
        testReplaceWithMismatchedOldValueReturnsFalse();
        testReplaceAbsentKeyReturnsFalseAndDoesNotInsert();
        testReplaceOnEmptyMapReturnsFalse();
        testNullOldValueMatchesNullMapping();
        testNullOldValueDoesNotMatchNonNullMapping();
        testNonNullOldValueDoesNotMatchNullMapping();
        testReplaceWithNullNewValueStoresNull();
        testComparatorBasedKeyMatching();
        testNullKeyWithNaturalOrderingThrowsNpe();
        testSortedMapViewStillReflectsReplace();
    }
}
