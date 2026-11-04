/*
 * @test
 * @summary Black-box checks for TreeMap.remove(Object)
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveOnEmptyMapReturnsNull();
        testRemoveAbsentKeyLeavesMapUnchanged();
        testRemovePresentKeyReturnsValueAndUpdatesMap();
        testRemoveMappingWithNullValueReturnsNullAndRemovesKey();
        testRemoveWithComparatorUsesComparatorEquality();
        testRemoveNullKeyWithNaturalOrderingThrowsNPE();
        testRemoveIncompatibleKeyTypeThrowsClassCastException();
    }

    static void testRemoveOnEmptyMapReturnsNull() {
        TreeMap<String, Integer> m = new TreeMap<>();
        check(m.remove("a") == null, "remove on empty map must return null");
        check(m.size() == 0, "size must stay 0");
    }

    static void testRemoveAbsentKeyLeavesMapUnchanged() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        m.put("c", 3);
        check(m.remove("z") == null, "absent key must yield null");
        check(m.size() == 2, "size must be unchanged");
        check(m.toString().equals("{a=1, c=3}"), "map contents changed: " + m);
    }

    static void testRemovePresentKeyReturnsValueAndUpdatesMap() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        Integer old = m.remove("b");
        check(old != null && old == 2, "remove must return previous value 2");
        check(!m.containsKey("b"), "key must be gone");
        check(m.size() == 2, "size must be 2");
        check(m.toString().equals("{a=1, c=3}"), "unexpected contents: " + m);
        check(m.remove("b") == null, "second remove must return null");
    }

    static void testRemoveMappingWithNullValueReturnsNullAndRemovesKey() {
        TreeMap<String, String> m = new TreeMap<>();
        m.put("a", null);
        check(m.containsKey("a"), "precondition: key present");
        check(m.remove("a") == null, "remove must return null value");
        check(!m.containsKey("a"), "key must be removed even though value was null");
        check(m.size() == 0, "size must be 0");
    }

    static void testRemoveWithComparatorUsesComparatorEquality() {
        TreeMap<String, String> m = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        m.put("Key", "v");
        check("v".equals(m.remove("KEY")), "comparator-equal key must be removed");
        check(m.size() == 0, "size must be 0 after removal");
    }

    static void testRemoveNullKeyWithNaturalOrderingThrowsNPE() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        try {
            m.remove(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        check(m.size() == 1, "map must be unchanged after failed remove");
    }

    static void testRemoveIncompatibleKeyTypeThrowsClassCastException() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        try {
            m.remove(Integer.valueOf(1));
            throw new RuntimeException("expected ClassCastException");
        } catch (ClassCastException expected) {
        }
        check(m.size() == 1, "map must be unchanged after failed remove");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
