/*
 * @test
 * @summary Verifies TreeMap values iterator next() returns values in key order, honors comparator order, supports remove interaction, and returns null values.
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        naturalOrderValuesInKeyOrder();
        reverseComparatorOrder();
        nextAfterIteratorRemove();
        nullValueReturned();
    }

    static void naturalOrderValuesInKeyOrder() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(3, "c");
        m.put(1, "a");
        m.put(2, "b");
        Iterator<String> it = m.values().iterator();
        check("a".equals(it.next()));
        check("b".equals(it.next()));
        check("c".equals(it.next()));
        check(!it.hasNext());
    }

    static void reverseComparatorOrder() {
        TreeMap<String, Integer> r = new TreeMap<>(Comparator.reverseOrder());
        r.put("x", 1);
        r.put("y", 2);
        Iterator<Integer> it = r.values().iterator();
        check(it.next() == 2);
        check(it.next() == 1);
    }

    static void nextAfterIteratorRemove() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        Iterator<String> it = m.values().iterator();
        check("a".equals(it.next()));
        it.remove();
        check("b".equals(it.next()));
        check(m.size() == 2);
        check(m.firstKey() == 2);
    }

    static void nullValueReturned() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, null);
        Iterator<String> it = m.values().iterator();
        check(it.next() == null);
        check(!it.hasNext());
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Assertion failed");
        }
    }
}
