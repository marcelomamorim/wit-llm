/*
 * @test
 * @summary Verify Spliterators.IntArraySpliterator.tryAdvance(IntConsumer) through the public Spliterator.OfInt API
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testEmptyArrayReturnsFalseWithoutInvokingAction();
        testAdvancesOneElementPerCall();
        testReturnsFalseAfterExhaustionWithoutInvokingAction();
    }

    static void testNullActionThrowsNullPointerException() {
        Spliterator.OfInt s = Spliterators.spliterator(new int[] {1, 2, 3}, 0);
        boolean thrown = false;
        try {
            s.tryAdvance((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException");
    }

    static void testEmptyArrayReturnsFalseWithoutInvokingAction() {
        Spliterator.OfInt s = Spliterators.spliterator(new int[0], 0);
        StringBuilder sb = new StringBuilder();
        boolean result = s.tryAdvance((IntConsumer) v -> sb.append(v).append(','));
        check(!result, "empty array must return false");
        check(sb.length() == 0, "action must not be invoked for empty array");
    }

    static void testAdvancesOneElementPerCall() {
        Spliterator.OfInt s = Spliterators.spliterator(new int[] {10, 20, 30}, 0);
        StringBuilder sb = new StringBuilder();
        IntConsumer action = v -> sb.append(v).append(',');

        check(s.tryAdvance(action), "first call must return true");
        check(sb.toString().equals("10,"), "first call must consume element 10, was: " + sb);

        check(s.tryAdvance(action), "second call must return true");
        check(sb.toString().equals("10,20,"), "second call must consume element 20, was: " + sb);

        check(s.tryAdvance(action), "third call must return true");
        check(sb.toString().equals("10,20,30,"), "third call must consume element 30, was: " + sb);
    }

    static void testReturnsFalseAfterExhaustionWithoutInvokingAction() {
        Spliterator.OfInt s = Spliterators.spliterator(new int[] {7}, 0);
        StringBuilder sb = new StringBuilder();
        IntConsumer action = v -> sb.append(v).append(',');

        check(s.tryAdvance(action), "first call must return true");
        check(!s.tryAdvance(action), "call after exhaustion must return false");
        check(sb.toString().equals("7,"), "action must not be invoked after exhaustion, was: " + sb);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
