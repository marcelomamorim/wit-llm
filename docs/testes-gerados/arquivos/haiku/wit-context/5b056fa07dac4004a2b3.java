/*
 * @test
 * @summary Tests Map.comparingByKey null handling, key ordering delegation and serializability
 * @run main MapWitupTest
 */

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class MapWitupTest {

    public static void main(String[] args) {
        testNullComparatorThrowsNPE();
        testOrdersByKeyDelegatingToComparator();
        testReverseComparatorDelegation();
        testResultIsSerializable();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullComparatorThrowsNPE() {
        boolean thrown = false;
        try {
            Map.comparingByKey(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "comparingByKey(null) must throw NullPointerException");
    }

    static void testOrdersByKeyDelegatingToComparator() {
        Comparator<String> natural = Comparator.naturalOrder();
        Comparator<Map.Entry<String, Integer>> c = Map.comparingByKey(natural);
        check(c.compare(Map.entry("a", 1), Map.entry("b", 2)) < 0,
              "key \"a\" must sort before \"b\"");
        check(c.compare(Map.entry("b", 1), Map.entry("a", 2)) > 0,
              "key \"b\" must sort after \"a\"");
        check(c.compare(Map.entry("a", 1), Map.entry("a", 99)) == 0,
              "entries with equal keys must compare as equal regardless of values");
    }

    static void testReverseComparatorDelegation() {
        Comparator<String> reverse = Collections.reverseOrder();
        Comparator<Map.Entry<String, Integer>> c = Map.comparingByKey(reverse);
        check(c.compare(Map.entry("a", 1), Map.entry("b", 2)) > 0,
              "reverse key comparator must invert ordering");
    }

    static void testResultIsSerializable() {
        Comparator<Map.Entry<String, Integer>> c =
            Map.comparingByKey(Comparator.<String>naturalOrder());
        check(c instanceof Serializable, "comparingByKey result must be Serializable");
    }
}
