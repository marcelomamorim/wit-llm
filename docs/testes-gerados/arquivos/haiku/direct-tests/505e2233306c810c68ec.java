/*
 * @test
 * @summary Verifies iteration behavior of immutable Map.of(...) entry set iterators (MapN.MapIterator.next)
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testIteratesAllEntriesOnce() {
        Map<String, Integer> m = Map.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 1);
        expected.put("b", 2);
        expected.put("c", 3);

        Iterator<Map.Entry<String, Integer>> it = m.entrySet().iterator();
        Set<String> seen = new HashSet<>();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry<String, Integer> e = it.next();
            count++;
            String k = e.getKey();
            check(expected.containsKey(k), "unexpected key: " + k);
            check(expected.get(k).equals(e.getValue()), "wrong value for key: " + k);
            check(seen.add(k), "key returned twice: " + k);
        }
        check(count == 3, "expected 3 entries but got " + count);
        check(seen.equals(expected.keySet()), "not all keys were visited");
    }

    static void testNextAfterExhaustionThrows() {
        Map<String, Integer> m = Map.of("x", 10, "y", 20, "z", 30);
        Iterator<Map.Entry<String, Integer>> it = m.entrySet().iterator();
        for (int i = 0; i < 3; i++) {
            check(it.hasNext(), "iterator ended early at " + i);
            it.next();
        }
        check(!it.hasNext(), "iterator should be exhausted");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException expected) {
            thrown = true;
        }
        check(thrown, "next() on exhausted iterator must throw NoSuchElementException");
    }

    static void testEntriesAreImmutable() {
        Map<String, Integer> m = Map.of("p", 1, "q", 2, "r", 3);
        Map.Entry<String, Integer> e = m.entrySet().iterator().next();
        boolean thrown = false;
        try {
            e.setValue(99);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }
        check(thrown, "entry from immutable map must not support setValue");
    }

    static void testIndependentIterators() {
        Map<String, Integer> m = Map.of("k1", 1, "k2", 2, "k3", 3);
        Iterator<Map.Entry<String, Integer>> first = m.entrySet().iterator();
        Iterator<Map.Entry<String, Integer>> second = m.entrySet().iterator();
        int c1 = 0;
        int c2 = 0;
        while (first.hasNext()) { first.next(); c1++; }
        while (second.hasNext()) { second.next(); c2++; }
        check(c1 == 3 && c2 == 3, "independent iterators must each see all entries");
    }

    public static void main(String[] args) {
        testIteratesAllEntriesOnce();
        testNextAfterExhaustionThrows();
        testEntriesAreImmutable();
        testIndependentIterators();
    }
}
