/*
 * @test
 * @summary Black-box checks of ConcurrentSkipListMap.replaceAll(BiFunction) null handling, value replacement, visit order and exception propagation
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullFunctionThrowsNpe();
        testEmptyMapInvokesNothing();
        testReplacesValuesUsingKeyAndValue();
        testNullReturnValueThrowsNpe();
        testVisitOrderFollowsComparator();
        testFunctionExceptionPropagates();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullFunctionThrowsNpe() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<Integer, String>();
        m.put(1, "a");
        try {
            m.replaceAll(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check("a".equals(m.get(1)), "value must be unchanged after null function");
    }

    static void testEmptyMapInvokesNothing() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<Integer, String>();
        final int[] calls = {0};
        m.replaceAll((k, v) -> {
            calls[0]++;
            return v;
        });
        check(calls[0] == 0, "function must not be called for empty map");
        check(m.isEmpty(), "empty map must remain empty");
    }

    static void testReplacesValuesUsingKeyAndValue() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<Integer, String>();
        m.put(1, "a");
        m.put(2, "b");
        m.replaceAll((k, v) -> k + v);
        check("1a".equals(m.get(1)), "key 1 should map to 1a but was " + m.get(1));
        check("2b".equals(m.get(2)), "key 2 should map to 2b but was " + m.get(2));
        check(m.size() == 2, "size must not change");
    }

    static void testNullReturnValueThrowsNpe() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<Integer, String>();
        m.put(1, "a");
        m.put(2, "b");
        try {
            m.replaceAll((k, v) -> k == 2 ? null : v + "x");
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
        check("b".equals(m.get(2)), "value for key whose function returned null must be unchanged");
    }

    static void testVisitOrderFollowsComparator() {
        ConcurrentSkipListMap<Integer, String> m =
            new ConcurrentSkipListMap<Integer, String>(Comparator.reverseOrder());
        m.put(1, "x");
        m.put(2, "y");
        m.put(3, "z");
        final List<Integer> seen = new ArrayList<Integer>();
        m.replaceAll((k, v) -> {
            seen.add(k);
            return v + "!";
        });
        check(seen.toString().equals("[3, 2, 1]"), "visit order must follow comparator, was " + seen);
        check("x!".equals(m.get(1)), "key 1 value wrong");
        check("y!".equals(m.get(2)), "key 2 value wrong");
        check("z!".equals(m.get(3)), "key 3 value wrong");
    }

    static void testFunctionExceptionPropagates() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<Integer, String>();
        m.put(1, "a");
        m.put(2, "b");
        try {
            m.replaceAll((k, v) -> {
                if (k == 2) {
                    throw new IllegalStateException("boom");
                }
                return v + "x";
            });
            throw new AssertionError("expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check("b".equals(m.get(2)), "value for key whose function threw must be unchanged");
    }
}
