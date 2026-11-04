/*
 * @test
 * @summary Tests Map.comparingByValue ordering, null handling and serializability
 * @run main MapWitupTest
 */

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MapWitupTest {

    public static void main(String[] args) {
        testNullComparatorRejected();
        testOrdersByValue();
        testIgnoresKeys();
        testReversedComparator();
        testSerializable();
        testSortEntries();
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static Map.Entry<String, Integer> e(String k, Integer v) {
        return new AbstractMap.SimpleEntry<>(k, v);
    }

    static void testNullComparatorRejected() {
        boolean thrown = false;
        try {
            Map.<String, Integer>comparingByValue(null);
        } catch (NullPointerException ex) {
            thrown = true;
        }
        check(thrown, "null comparator must be rejected with NullPointerException");
    }

    static void testOrdersByValue() {
        Comparator<Map.Entry<String, Integer>> c =
            Map.comparingByValue(Comparator.<Integer>naturalOrder());
        check(c.compare(e("x", 1), e("y", 2)) < 0, "value 1 should sort before 2");
        check(c.compare(e("x", 3), e("y", 2)) > 0, "value 3 should sort after 2");
    }

    static void testIgnoresKeys() {
        Comparator<Map.Entry<String, Integer>> c =
            Map.comparingByValue(Comparator.<Integer>naturalOrder());
        check(c.compare(e("a", 5), e("z", 5)) == 0, "equal values must compare as equal regardless of keys");
    }

    static void testReversedComparator() {
        Comparator<Map.Entry<String, Integer>> r =
            Map.comparingByValue(Comparator.<Integer>reverseOrder());
        check(r.compare(e("x", 1), e("y", 2)) > 0, "reversed value order should place 1 after 2");
    }

    static void testSerializable() {
        Comparator<Map.Entry<String, Integer>> c =
            Map.comparingByValue(Comparator.<Integer>naturalOrder());
        Object o = c;
        check(o instanceof Serializable, "comparingByValue result must be Serializable");
    }

    static void testSortEntries() {
        Comparator<Map.Entry<String, Integer>> c =
            Map.comparingByValue(Comparator.<Integer>naturalOrder());
        List<Map.Entry<String, Integer>> l = new ArrayList<>();
        l.add(e("c", 3));
        l.add(e("a", 1));
        l.add(e("b", 2));
        l.sort(c);
        check(l.get(0).getKey().equals("a"), "first entry should be a");
        check(l.get(1).getKey().equals("b"), "second entry should be b");
        check(l.get(2).getKey().equals("c"), "third entry should be c");
    }
}
