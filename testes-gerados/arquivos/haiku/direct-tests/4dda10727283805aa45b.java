/*
 * @test
 * @summary ThreadLocalRandom.doubles() returns a sequential, unlimited stream of doubles in [0.0, Double.MAX_VALUE)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testStreamIsSequential();
        testZeroLimitYieldsNoElements();
        testValuesAreWithinBounds();
        testValuesAreNotAllEqual();
    }

    static void testStreamIsSequential() {
        DoubleStream s = ThreadLocalRandom.current().doubles();
        try {
            if (s.isParallel()) {
                throw new RuntimeException("doubles() must return a sequential stream");
            }
        } finally {
            s.close();
        }
    }

    static void testZeroLimitYieldsNoElements() {
        long count = ThreadLocalRandom.current().doubles().limit(0).count();
        if (count != 0L) {
            throw new RuntimeException("expected 0 elements, got " + count);
        }
    }

    static void testValuesAreWithinBounds() {
        double[] values = ThreadLocalRandom.current().doubles().limit(1000).toArray();
        if (values.length != 1000) {
            throw new RuntimeException("expected 1000 elements, got " + values.length);
        }
        for (double v : values) {
            if (!(v >= 0.0 && v < Double.MAX_VALUE)) {
                throw new RuntimeException("value out of range [0.0, Double.MAX_VALUE): " + v);
            }
        }
    }

    static void testValuesAreNotAllEqual() {
        double[] values = ThreadLocalRandom.current().doubles().limit(100).toArray();
        for (int i = 1; i < values.length; i++) {
            if (values[i] != values[0]) {
                return;
            }
        }
        throw new RuntimeException("100 generated doubles were all identical");
    }
}
