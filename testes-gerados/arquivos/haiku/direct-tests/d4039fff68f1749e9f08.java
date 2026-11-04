/*
 * @test
 * @summary Checks SplittableRandom.forEachRemaining(DoubleConsumer) on the double spliterator: values, exhaustion, and null handling.
 * @run main SplittableRandomWitupTest
 */

import java.util.Spliterator;
import java.util.SplittableRandom;
import java.util.function.DoubleConsumer;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        long seed = 42L;
        int n = 16;

        Spliterator.OfDouble sp = new SplittableRandom(seed).doubles(n, 0.0, 1.0).spliterator();
        double[] got = new double[n];
        int[] idx = {0};
        sp.forEachRemaining((DoubleConsumer) d -> got[idx[0]++] = d);

        if (idx[0] != n) {
            throw new RuntimeException("expected " + n + " elements, got " + idx[0]);
        }

        SplittableRandom ref = new SplittableRandom(seed);
        for (int i = 0; i < n; i++) {
            double expected = ref.nextDouble();
            if (got[i] != expected) {
                throw new RuntimeException("mismatch at " + i + ": " + got[i] + " != " + expected);
            }
        }

        int[] extra = {0};
        sp.forEachRemaining((DoubleConsumer) d -> extra[0]++);
        if (extra[0] != 0) {
            throw new RuntimeException("spliterator should be exhausted, got " + extra[0] + " extra");
        }

        boolean npe = false;
        try {
            new SplittableRandom(seed).doubles(n, 0.0, 1.0).spliterator()
                    .forEachRemaining((DoubleConsumer) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null consumer");
        }
    }
}
