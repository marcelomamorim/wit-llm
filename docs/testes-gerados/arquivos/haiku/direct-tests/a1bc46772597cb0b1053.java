/*
 * @test
 * @summary Verify that the key iterator's next() returns keys in TreeMap order
 * @run main TreeMapWitupTest
 */

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNaturalOrderStrings();
        testNaturalOrderIntegersNotLexicographic();
        testReverseComparator();
        testCopyConstructorOrder();
        testSingleElement();
        testIteratorRemoveThenContinue();
    }

    static void testNaturalOrderStrings() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("b", 2);
        m.put("a", 1);
        m.put("c", 3);
        Iterator<String> it = m.keySet().iterator();
        check("a", it.next());
        check("b", it.next());
        check("c", it.next());
        check(Boolean.FALSE, it.hasNext());
    }

    static void testNaturalOrderIntegersNotLexicographic() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(10, "x");
        m.put(9, "y");
        m.put(2, "z");
        Iterator<Integer> it = m.keySet().iterator();
        check(Integer.valueOf(2), it.next());
        check(Integer.valueOf(9), it.next());
        check(Integer.valueOf(10), it.next());
    }

    static void testReverseComparator() {
        TreeMap<Integer, String> m = new TreeMap<>(Comparator.reverseOrder());
        m.put(1, "a");
        m.put(3, "c");
        m.put(2, "b");
        Iterator<Integer> it = m.keySet().iterator();
        check(Integer.valueOf(3), it.next());
        check(Integer.valueOf(2), it.next());
        check(Integer.valueOf(1), it.next());
    }

    static void testCopyConstructorOrder() {
        TreeMap<String, Integer> src = new TreeMap<>();
        src.put("z", 26);
        src.put("m", 13);
        src.put("a", 1);
        TreeMap<String, Integer> copy = new TreeMap<>((Map<String, Integer>) src);
        Iterator<String> it = copy.keySet().iterator();
        check("a", it.next());
        check("m", it.next());
        check("z", it.next());
    }

    static void testSingleElement() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("only", 1);
        Iterator<String> it = m.keySet().iterator();
        check("only", it.next());
        check(Boolean.FALSE, it.hasNext());
    }

    static void testIteratorRemoveThenContinue() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        Iterator<String> it = m.keySet().iterator();
        check("a", it.next());
        it.remove();
        check("b", it.next());
        check("c", it.next());
        check(Boolean.FALSE, it.hasNext());
        check(Integer.valueOf(2), m.size() == 2 ? Integer.valueOf(2) : Integer.valueOf(-1));
    }

    static void check(Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
