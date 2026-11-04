/*
 * @test
 * @summary Test java.util.SplittableRandom.tryAdvance(DoubleConsumer) behavior
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.function.DoubleConsumer;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        testTryAdvanceReturnsTrueAndAcceptsValue();
        testTryAdvanceReturnsFalseWhenExhausted();
        testTryAdvanceThrowsNPEOnNullConsumer();
    }

    static void testTryAdvanceReturnsTrueAndAcceptsValue() {
        final SplittableRandom sr = new SplittableRandom(12345L);
        final double[] accepted = new double[1];
        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double value) {
                accepted[0] = value;
            }
        };
        boolean result = sr.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true when index < fence");
        }
        // The accepted value should be within [0.0,1.0) as per internalNextDouble contract
        if (accepted[0] < 0.0 || accepted[0] >= 1.0) {
            throw new AssertionError("Accepted value " + accepted[0] + " out of expected range [0.0,1.0)");
        }
    }

    static void testTryAdvanceReturnsFalseWhenExhausted() {
        // We cannot directly set index and fence, so simulate exhaustion by calling tryAdvance repeatedly
        final SplittableRandom sr = new SplittableRandom(12345L);
        final double[] accepted = new double[1];
        DoubleConsumer consumer = new DoubleConsumer() {
            public void accept(double value) {
                accepted[0] = value;
            }
        };

        // tryAdvance returns true at least once
        boolean anyTrue = false;
        for (int i = 0; i < 1000; i++) {
            if (sr.tryAdvance(consumer)) {
                anyTrue = true;
            } else {
                // Once false is returned, test passes
                if (!anyTrue) {
                    throw new AssertionError("tryAdvance returned false before any true");
                }
                return;
            }
        }
        // If after 1000 calls it never returned false, fail test
        throw new AssertionError("tryAdvance never returned false after 1000 calls");
    }

    static void testTryAdvanceThrowsNPEOnNullConsumer() {
        final SplittableRandom sr = new SplittableRandom(12345L);
        try {
            sr.tryAdvance(null);
            throw new AssertionError("tryAdvance should throw NullPointerException when consumer is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
