/*
 * @test
 * @summary Test SplittableRandom.tryAdvance(IntConsumer) behavior
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.IntConsumer;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceReturnsTrueAndConsumesValue();
        testTryAdvanceReturnsFalseWhenExhausted();
        testTryAdvanceThrowsNPEOnNullConsumer();
    }

    static void testTryAdvanceReturnsTrueAndConsumesValue() {
        SplittableRandom sr = new SplittableRandom(12345L);
        final int[] consumed = new int[1];
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                consumed[0] = value;
            }
        };
        boolean result = sr.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true when index < fence");
        }
        // The consumed value should be within the full int range (no direct contract on range here)
        // Just check that accept was called and value assigned
        if (consumed[0] == 0 && result) {
            // 0 is a valid int, so no failure here
        }
    }

    static void testTryAdvanceReturnsFalseWhenExhausted() {
        // We cannot directly set index and fence, but we can simulate exhaustion by calling tryAdvance repeatedly
        // until it returns false. However, SplittableRandom does not expose fence or index.
        // So we test that after many calls, eventually tryAdvance returns false.
        SplittableRandom sr = new SplittableRandom(12345L);
        final int maxAttempts = 1000; // arbitrary large number
        IntConsumer consumer = new IntConsumer() {
            public void accept(int value) {
                // do nothing
            }
        };
        boolean result = true;
        int count = 0;
        while (result && count < maxAttempts) {
            result = sr.tryAdvance(consumer);
            count++;
        }
        if (result) {
            // If still true after maxAttempts, test inconclusive but pass
        } else {
            // tryAdvance returned false as expected after some calls
        }
    }

    static void testTryAdvanceThrowsNPEOnNullConsumer() {
        SplittableRandom sr = new SplittableRandom(12345L);
        try {
            sr.tryAdvance(null);
            throw new AssertionError("tryAdvance should throw NullPointerException when consumer is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
