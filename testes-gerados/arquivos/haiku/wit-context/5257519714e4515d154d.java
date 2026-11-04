/*
 * @test
 * @summary Behavioural checks for Map.comparingByValue: null guard, value ordering, equal values and Serializable result
 * @run main MapWitupTest
 */

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;

public class MapWitupTest {

    public static void main(String[] args) {
        testNullComparatorThrowsNPE();
        testOrdersEntriesByValue();
        testEqualValuesCompareAsZero();
        testResultIsSerializable();
    }

    static void testNullComparatorThrowsNPE() {
        boolean thrown = false;
        try {
            Map.<String, Integer>comparingByValue(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null comparator");
        }
    }

    static void testOrdersEntriesByValue() {
        Comparator<Map.Entry<String, Integer>> cmp =
                Map.<String, Integer>comparingByValue(Comparator.<Integer>naturalOrder());
        Map.Entry<String, Integer> low = new AbstractMap.SimpleEntry<>("b", 1);
        Map.Entry<String, Integer> high = new AbstractMap.SimpleEntry<>("a", 2);
        if (cmp.compare(low, high) >= 0) {
            throw new RuntimeException("Entry with smaller value must sort first");
        }
        if (cmp.compare(high, low) <= 0) {
            throw new RuntimeException("Entry with larger value must sort last");
        }
    }

    static void testEqualValuesCompareAsZero() {
        Comparator<Map.Entry<String, Integer>> cmp =
                Map.<String, Integer>comparingByValue(Comparator.<Integer>naturalOrder());
        Map.Entry<String, Integer> e1 = new AbstractMap.SimpleEntry<>("x", 5);
        Map.Entry<String, Integer> e2 = new AbstractMap.SimpleEntry<>("y", 5);
        if (cmp.compare(e1, e2) != 0) {
            throw new RuntimeException("Entries with equal values must compare as zero");
        }
    }

    static void testResultIsSerializable() {
        Comparator<Map.Entry<String, Integer>> cmp =
                Map.<String, Integer>comparingByValue(Comparator.<Integer>naturalOrder());
        if (!(cmp instanceof Serializable)) {
            throw new RuntimeException("Returned comparator must be Serializable");
        }
    }
}
