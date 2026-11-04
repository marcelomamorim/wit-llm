/*
 * @test
 * @summary WITUP-derived checks for Spliterators IntArraySpliterator.tryAdvance via the public int[] spliterator factory
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        testNullActionThrowsNpeWithoutConsumingElement();
        testSequentialAdvanceReturnsElementsInOrder();
        testExhaustedSpliteratorReturnsFalse();
        testEmptyArrayReturnsFalse();
    }

    static void testNullActionThrowsNpeWithoutConsumingElement() {
        int[] data = {10, 20, 30};
        Spliterator.OfInt sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        boolean thrown = false;
        try {
            sp.tryAdvance((java.util.function.IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");

        final int[] seen = new int[1];
        boolean advanced = sp.tryAdvance((java.util.function.IntConsumer) v -> seen[0] = v);
        check(advanced, "spliterator must still have elements after rejected null action");
        check(seen[0] == 10, "first element must remain 10 after rejected null action, was " + seen[0]);
    }

    static void testSequentialAdvanceReturnsElementsInOrder() {
        int[] data = {10, 20, 30};
        Spliterator.OfInt sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        final int[] out = new int[data.length];
        final int[] count = new int[1];
        for (int i = 0; i < data.length; i++) {
            boolean r = sp.tryAdvance((java.util.function.IntConsumer) v -> out[count[0]++] = v);
            check(r, "tryAdvance should return true at index " + i);
        }
        check(count[0] == 3, "expected 3 consumed elements, got " + count[0]);
        check(out[0] == 10 && out[1] == 20 && out[2] == 30, "unexpected element order");
    }

    static void testExhaustedSpliteratorReturnsFalse() {
        int[] data = {7};
        Spliterator.OfInt sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        final int[] calls = new int[1];
        java.util.function.IntConsumer c = v -> calls[0]++;
        check(sp.tryAdvance(c), "first tryAdvance should return true");
        check(!sp.tryAdvance(c), "tryAdvance should return false when exhausted");
        check(!sp.tryAdvance(c), "repeated tryAdvance should keep returning false");
        check(calls[0] == 1, "consumer must be invoked exactly once, was " + calls[0]);
    }

    static void testEmptyArrayReturnsFalse() {
        int[] data = new int[0];
        Spliterator.OfInt sp = Spliterators.spliterator(data, Spliterator.ORDERED);
        final int[] calls = new int[1];
        boolean r = sp.tryAdvance((java.util.function.IntConsumer) v -> calls[0]++);
        check(!r, "tryAdvance on empty array must return false");
        check(calls[0] == 0, "consumer must not be invoked for empty array");
    }
}
