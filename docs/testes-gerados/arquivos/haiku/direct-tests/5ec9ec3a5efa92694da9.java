/*
 * @test
 * @summary Checks Random.ints() spliterator tryAdvance: NPE on null consumer, bounded deterministic values, and exhaustion after the stream size is reached.
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.Spliterator;
import java.util.function.IntConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testValuesMatchIntsStreamAndAreInRange();
        testReturnsFalseAfterFenceWithoutCallingConsumer();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfInt s = new Random(1L).ints(2, 0, 10).spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance((IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "NullPointerException expected for null consumer");
    }

    static void testValuesMatchIntsStreamAndAreInRange() {
        int[] expected = new Random(42L).ints(3, 0, 100).toArray();
        Spliterator.OfInt s = new Random(42L).ints(3, 0, 100).spliterator();
        int[] box = new int[1];
        IntConsumer capture = v -> box[0] = v;
        for (int k = 0; k < 3; k++) {
            boolean advanced = s.tryAdvance(capture);
            check(advanced, "tryAdvance should return true at index " + k);
            check(box[0] == expected[k], "value mismatch at index " + k);
            check(box[0] >= 0 && box[0] < 100, "value out of range at index " + k);
        }
    }

    static void testReturnsFalseAfterFenceWithoutCallingConsumer() {
        Spliterator.OfInt s = new Random(7L).ints(2, 5, 10).spliterator();
        IntConsumer noop = v -> { };
        check(s.tryAdvance(noop), "first element expected");
        check(s.tryAdvance(noop), "second element expected");
        int[] calls = new int[1];
        boolean again = s.tryAdvance(v -> calls[0]++);
        check(!again, "tryAdvance should return false after fence");
        check(calls[0] == 0, "consumer must not be called after fence");
    }
}
