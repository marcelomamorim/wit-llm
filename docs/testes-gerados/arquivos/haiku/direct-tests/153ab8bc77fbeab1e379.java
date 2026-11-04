/*
 * @test
 * @summary Behavioral tests for TreeMap.remove(Object key)
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveExistingKeyReturnsValue();
        testRemoveMissingKeyReturnsNull();
        testRemoveFromEmptyMapReturnsNull();
        testRemoveTwiceReturnsNullSecondTime();
        testRemoveNullValueReturnsNullButDeletesMapping();
        testRemoveUsesComparatorEquivalence();
        testRemoveKeepsOtherEntries();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveExistingKeyReturnsValue() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        m.put("b", 2);
        Integer removed = m.remove("a");
        check(removed != null && removed == 1, "remove should return old value 1");
        check(!m.containsKey("a"), "key should no longer be present");
        check(m.size() == 1, "size should be 1 but was " + m.size());
    }

    static void testRemoveMissingKeyReturnsNull() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        m.put("b", 2);
        check(m.remove("z") == null, "remove of missing key should return null");
        check(m.size() == 2, "size should remain 2");
        check(m.get("a") == 1 && m.get("b") == 2, "existing mappings should be unchanged");
    }

    static void testRemoveFromEmptyMapReturnsNull() {
        TreeMap<String, Integer> m = new TreeMap<>();
        check(m.remove("x") == null, "remove on empty map should return null");
        check(m.isEmpty(), "map should remain empty");
    }

    static void testRemoveTwiceReturnsNullSecondTime() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        check(m.remove("a") == 1, "first remove should return 1");
        check(m.remove("a") == null, "second remove should return null");
        check(m.isEmpty(), "map should be empty");
    }

    static void testRemoveNullValueReturnsNullButDeletesMapping() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", null);
        check(m.containsKey("a"), "precondition: key with null value present");
        check(m.remove("a") == null, "remove should return null value");
        check(!m.containsKey("a"), "mapping should be deleted even though value was null");
        check(m.size() == 0, "size should be 0");
    }

    static void testRemoveUsesComparatorEquivalence() {
        TreeMap<String, Integer> m = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        m.put("Key", 1);
        Integer removed = m.remove("KEY");
        check(removed != null && removed == 1, "remove should find key via comparator equivalence");
        check(m.isEmpty(), "map should be empty after removal");
    }

    static void testRemoveKeepsOtherEntries() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        check(m.remove("b") == 2, "remove middle key should return 2");
        check(m.size() == 2, "size should be 2");
        check(m.get("a") == 1 && m.get("c") == 3, "other mappings should be preserved");
        check(m.firstKey().equals("a") && m.lastKey().equals("c"), "ordering should be preserved");
    }
}
