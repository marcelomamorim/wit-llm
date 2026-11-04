/*
 * @test
 * @summary Witup-derived checks for TreeMap values spliterator forEachRemaining (null action NPE, ordered traversal, empty map)
 * @run main TreeMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testValuesTraversedInKeyOrder();
        testEmptyMapDoesNotInvokeAction();
    }

    static void testNullActionThrowsNPE() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        Spliterator<Integer> s = m.values().spliterator();
        try {
            s.forEachRemaining(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null action");
    }

    static void testValuesTraversedInKeyOrder() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("c", 3);
        m.put("a", 1);
        m.put("b", 2);
        Spliterator<Integer> s = m.values().spliterator();
        List<Integer> seen = new ArrayList<>();
        s.forEachRemaining(seen::add);
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        if (!expected.equals(seen)) {
            throw new AssertionError("expected " + expected + " but was " + seen);
        }
    }

    static void testEmptyMapDoesNotInvokeAction() {
        TreeMap<String, Integer> m = new TreeMap<>();
        Spliterator<Integer> s = m.values().spliterator();
        List<Integer> seen = new ArrayList<>();
        s.forEachRemaining(seen::add);
        if (!seen.isEmpty()) {
            throw new AssertionError("action should not be invoked for empty map: " + seen);
        }
    }
}
