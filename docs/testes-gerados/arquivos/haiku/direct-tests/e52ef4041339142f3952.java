/*
 * @test
 * @summary Verifies Hashtable enumerator nextElement behavior for keys, values and exhaustion
 * @run main HashtableWitupTest
 */

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class HashtableWitupTest {

    public static void main(String[] args) {
        testEmptyTableThrowsNoSuchElement();
        testKeysEnumerationReturnsAllKeysOnce();
        testElementsEnumerationReturnsAllValuesOnce();
        testSingleEntry();
        testCopyConstructorEnumeration();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNoSuchElement(Enumeration<?> e) {
        try {
            e.nextElement();
        } catch (NoSuchElementException ex) {
            return;
        }
        throw new RuntimeException("expected NoSuchElementException");
    }

    static <T> List<T> drain(Enumeration<T> e) {
        List<T> list = new ArrayList<>();
        while (e.hasMoreElements()) {
            list.add(e.nextElement());
        }
        return list;
    }

    static void testEmptyTableThrowsNoSuchElement() {
        Hashtable<String, Integer> h = new Hashtable<>();
        Enumeration<String> keys = h.keys();
        check(!keys.hasMoreElements(), "empty table keys should have no elements");
        expectNoSuchElement(keys);

        Enumeration<Integer> values = h.elements();
        check(!values.hasMoreElements(), "empty table elements should have no elements");
        expectNoSuchElement(values);
    }

    static void testKeysEnumerationReturnsAllKeysOnce() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 1);
        h.put("b", 2);
        h.put("c", 3);

        Enumeration<String> keys = h.keys();
        List<String> got = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            got.add(keys.nextElement());
        }
        Set<String> set = new HashSet<>(got);
        check(got.size() == 3, "expected 3 keys returned");
        check(set.size() == 3, "keys should not repeat");
        check(set.contains("a") && set.contains("b") && set.contains("c"),
              "keys enumeration missing expected key");
        expectNoSuchElement(keys);
    }

    static void testElementsEnumerationReturnsAllValuesOnce() {
        Hashtable<String, Integer> h = new Hashtable<>();
        h.put("a", 10);
        h.put("b", 20);
        h.put("c", 30);

        Enumeration<Integer> values = h.elements();
        List<Integer> got = drain(values);
        Set<Integer> set = new HashSet<>(got);
        check(got.size() == 3, "expected 3 values returned");
        check(set.equals(new HashSet<>(List.of(10, 20, 30))) || containsAll(set, 10, 20, 30),
              "values enumeration returned wrong values");
        expectNoSuchElement(values);
    }

    static boolean containsAll(Set<Integer> set, int... vals) {
        for (int v : vals) {
            if (!set.contains(v)) {
                return false;
            }
        }
        return set.size() == vals.length;
    }

    static void testSingleEntry() {
        Hashtable<String, String> h = new Hashtable<>();
        h.put("x", "y");

        Enumeration<String> keys = h.keys();
        check(keys.hasMoreElements(), "single entry should yield one key");
        check("x".equals(keys.nextElement()), "single key should be x");
        check(!keys.hasMoreElements(), "no more keys expected");
        expectNoSuchElement(keys);

        Enumeration<String> values = h.elements();
        check("y".equals(values.nextElement()), "single value should be y");
        expectNoSuchElement(values);
    }

    static void testCopyConstructorEnumeration() {
        Map<String, Integer> source = new HashMap<>();
        source.put("one", 1);
        source.put("two", 2);

        Hashtable<String, Integer> h = new Hashtable<>(source);
        Set<String> keys = new HashSet<>(drain(h.keys()));
        check(keys.equals(source.keySet()), "copied table keys should match source keys");

        Set<Integer> values = new HashSet<>(drain(h.elements()));
        check(values.equals(new HashSet<>(source.values())),
              "copied table values should match source values");
    }
}
