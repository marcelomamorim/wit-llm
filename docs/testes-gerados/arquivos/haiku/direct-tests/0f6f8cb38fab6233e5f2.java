/*
 * @test
 * @summary Behavioral tests for TreeMap iterator remove() via public keySet().iterator()
 * @run main TreeMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutNextThrowsISE();
        testRemoveTwiceThrowsISE();
        testRemoveAfterNextDeletesEntry();
        testRemoveDetectsConcurrentModification();
        testRemoveWithTwoChildrenKeepsIterationComplete();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testRemoveWithoutNextThrowsISE() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        Iterator<Integer> it = m.keySet().iterator();
        try {
            it.remove();
            throw new AssertionError("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(m.size() == 1, "map must be unchanged");
    }

    static void testRemoveTwiceThrowsISE() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        Iterator<Integer> it = m.keySet().iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new AssertionError("expected IllegalStateException on second remove");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(m.size() == 1, "size must be 1 after one removal");
    }

    static void testRemoveAfterNextDeletesEntry() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        Iterator<Integer> it = m.keySet().iterator();
        check(it.next() == 1, "first key must be 1");
        it.remove();
        check(!m.containsKey(1), "key 1 must be removed");
        check(m.size() == 2, "size must be 2");
        List<Integer> keys = new ArrayList<>(m.keySet());
        check(keys.toString().equals("[2, 3]"), "remaining keys must be [2, 3] but was " + keys);
    }

    static void testRemoveDetectsConcurrentModification() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        Iterator<Integer> it = m.keySet().iterator();
        it.next();
        m.put(3, "c");
        try {
            it.remove();
            throw new AssertionError("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void testRemoveWithTwoChildrenKeepsIterationComplete() {
        TreeMap<Integer, String> m = new TreeMap<>();
        int[] inserted = {5, 3, 8, 1, 4, 7, 9};
        for (int k : inserted) {
            m.put(k, "v" + k);
        }
        List<Integer> visited = new ArrayList<>();
        Iterator<Integer> it = m.keySet().iterator();
        while (it.hasNext()) {
            int k = it.next();
            visited.add(k);
            if (k % 2 == 1) {
                it.remove();
            }
        }
        check(visited.toString().equals("[1, 3, 4, 5, 7, 8, 9]"),
              "iteration must visit every key once, visited=" + visited);
        List<Integer> remaining = new ArrayList<>(m.keySet());
        check(remaining.toString().equals("[4, 8]"),
              "remaining keys must be [4, 8] but was " + remaining);
    }
}
