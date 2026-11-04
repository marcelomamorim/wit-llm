/*
 * @test
 * @summary Verify Map.comparingByKey null handling, key-based ordering, equal keys and Serializable result
 * @run main MapWitupTest
 */

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MapWitupTest {

    public static void main(String[] args) {
        testNullComparatorRejected();
        testNaturalKeyOrdering();
        testReverseKeyOrdering();
        testEqualKeysCompareToZero();
        testResultIsSerializable();
        testSortingEntriesByKey();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static Map.Entry<String, Integer> entry(String key, int value) {
        return new AbstractMap.SimpleEntry<String, Integer>(key, value);
    }

    static void testNullComparatorRejected() {
        boolean thrown = false;
        try {
            Map.<String, Integer>comparingByKey(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "comparingByKey(null) must throw NullPointerException");
    }

    static void testNaturalKeyOrdering() {
        Comparator<Map.Entry<String, Integer>> c =
            Map.<String, Integer>comparingByKey(Comparator.<String>naturalOrder());
        check(c.compare(entry("a", 1), entry("b", 2)) < 0, "a should sort before b");
        check(c.compare(entry("b", 1), entry("a", 2)) > 0, "b should sort after a");
    }

    static void testReverseKeyOrdering() {
        Comparator<Map.Entry<String, Integer>> c =
            Map.<String, Integer>comparingByKey(Comparator.<String>reverseOrder());
        check(c.compare(entry("a", 1), entry("b", 2)) > 0, "reverse: a should sort after b");
        check(c.compare(entry("b", 1), entry("a", 2)) < 0, "reverse: b should sort before a");
    }

    static void testEqualKeysCompareToZero() {
        Comparator<Map.Entry<String, Integer>> c =
            Map.<String, Integer>comparingByKey(Comparator.<String>naturalOrder());
        check(c.compare(entry("a", 1), entry("a", 9)) == 0,
              "entries with equal keys must compare as zero regardless of values");
    }

    static void testResultIsSerializable() {
        Comparator<Map.Entry<String, Integer>> c =
            Map.<String, Integer>comparingByKey(Comparator.<String>naturalOrder());
        check(c instanceof Serializable, "comparingByKey result must be Serializable");
    }

    static void testSortingEntriesByKey() {
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>();
        list.add(entry("c", 3));
        list.add(entry("a", 1));
        list.add(entry("b", 2));
        Collections.sort(list,
            Map.<String, Integer>comparingByKey(Comparator.<String>naturalOrder()));
        check(list.get(0).getKey().equals("a"), "first key should be a");
        check(list.get(1).getKey().equals("b"), "second key should be b");
        check(list.get(2).getKey().equals("c"), "third key should be c");
    }
}
