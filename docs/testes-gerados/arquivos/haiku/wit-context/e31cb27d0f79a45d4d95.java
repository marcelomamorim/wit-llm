/*
 * @test
 * @summary Tests RandomDoublesSpliterator.tryAdvance via the public doubles(long) spliterator: null consumer NPE, deterministic values, and exhaustion behavior
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.Spliterator;
import java.util.function.DoubleConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testDeterministicValuesAndExhaustion();
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfDouble sp = new SplittableRandom(42L).doubles(3).spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance((DoubleConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
    }

    static void testDeterministicValuesAndExhaustion() {
        double[] first = drain(new SplittableRandom(7L).doubles(5).spliterator(), 5);
        double[] second = drain(new SplittableRandom(7L).doubles(5).spliterator(), 5);
        for (int i = 0; i < 5; i++) {
            check(first[i] >= 0.0 && first[i] < 1.0, "value out of [0,1): " + first[i]);
            check(Double.doubleToLongBits(first[i]) == Double.doubleToLongBits(second[i]),
                  "same seed must produce same sequence at index " + i);
        }

        Spliterator.OfDouble sp = new SplittableRandom(7L).doubles(2).spliterator();
        final int[] calls = new int[1];
        DoubleConsumer counter = v -> calls[0]++;
        check(sp.tryAdvance(counter), "first tryAdvance should return true");
        check(sp.tryAdvance(counter), "second tryAdvance should return true");
        check(!sp.tryAdvance(counter), "tryAdvance after exhaustion should return false");
        check(calls[0] == 2, "consumer must not be invoked after exhaustion, calls=" + calls[0]);
    }

    static double[] drain(Spliterator.OfDouble sp, int n) {
        final double[] out = new double[n];
        final int[] idx = new int[1];
        for (int i = 0; i < n; i++) {
            boolean advanced = sp.tryAdvance((DoubleConsumer) v -> out[idx[0]++] = v);
            check(advanced, "expected element at position " + i);
        }
        return out;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
