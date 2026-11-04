/*
 * @test
 * @summary Behavioral tests for TreeMap value spliterator forEachRemaining
 * @run main TreeMapWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;
import java.util.TreeMap;
import java.util.function.Consumer;

public class TreeMapWitupTest {
    static boolean modified = false;
    static TreeMap<String, Integer> shared;

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        testVisitsValuesInKeyOrder();
        testNullActionThrowsNPE();
        testRemainingAfterTryAdvance();
        testEmptyMapNoCalls();
        testExhaustedSpliteratorTryAdvanceFalse();
        testStructuralModificationThrowsCME();
    }

    static void testVisitsValuesInKeyOrder() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("b", 2);
        m.put("a", 1);
        m.put("c", 3);
        List<Integer> seen = new ArrayList<>();
        Consumer<Integer> c = seen::add;
        m.values().spliterator().forEachRemaining(c);
        check(seen.equals(Arrays.asList(1, 2, 3)), "unexpected order: " + seen);
    }

    static void testNullActionThrowsNPE() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        Spliterator<Integer> s = m.values().spliterator();
        try {
            s.forEachRemaining(null);
            throw new RuntimeException("NullPointerException expected");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRemainingAfterTryAdvance() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        Spliterator<Integer> s = m.values().spliterator();
        List<Integer> first = new ArrayList<>();
        Consumer<Integer> addFirst = first::add;
        check(s.tryAdvance(addFirst), "tryAdvance should succeed");
        check(first.equals(Arrays.asList(1)), "first element wrong: " + first);
        List<Integer> rest = new ArrayList<>();
        Consumer<Integer> addRest = rest::add;
        s.forEachRemaining(addRest);
        check(rest.equals(Arrays.asList(2, 3)), "remaining wrong: " + rest);
    }

    static void testEmptyMapNoCalls() {
        TreeMap<String, Integer> m = new TreeMap<>();
        List<Integer> seen = new ArrayList<>();
        Consumer<Integer> c = seen::add;
        m.values().spliterator().forEachRemaining(c);
        check(seen.isEmpty(), "empty map should produce no calls");
    }

    static void testExhaustedSpliteratorTryAdvanceFalse() {
        TreeMap<String, Integer> m = new TreeMap<>();
        m.put("a", 1);
        m.put("b", 2);
        Spliterator<Integer> s = m.values().spliterator();
        List<Integer> seen = new ArrayList<>();
        Consumer<Integer> c = seen::add;
        s.forEachRemaining(c);
        check(seen.equals(Arrays.asList(1, 2)), "full traversal wrong: " + seen);
        check(!s.tryAdvance(c), "spliterator should be exhausted");
        check(seen.size() == 2, "no extra elements expected");
    }

    static void testStructuralModificationThrowsCME() {
        shared = new TreeMap<>();
        shared.put("a", 1);
        shared.put("b", 2);
        modified = false;
        Spliterator<Integer> s = shared.values().spliterator();
        Consumer<Integer> c = v -> {
            if (!modified) {
                modified = true;
                shared.put("z", 99);
            }
        };
        try {
            s.forEachRemaining(c);
            throw new RuntimeException("ConcurrentModificationException expected");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
