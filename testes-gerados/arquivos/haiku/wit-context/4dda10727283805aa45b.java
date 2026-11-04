/*
 * @test
 * @summary Checks the public contract of ThreadLocalRandom.doubles(): non-null stream, bounded values in [0.0, 1.0), and finite consumption via limit.
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testDoublesReturnsNonNullStream();
        testDoublesValuesWithinUnitInterval();
        testDoublesLimitProducesExactCount();
        testDoublesCountOnLimitedStream();
    }

    static void testDoublesReturnsNonNullStream() {
        DoubleStream s = ThreadLocalRandom.current().doubles();
        if (s == null) {
            throw new AssertionError("doubles() returned null");
        }
        s.close();
    }

    static void testDoublesValuesWithinUnitInterval() {
        double[] values = ThreadLocalRandom.current().doubles().limit(10000).toArray();
        if (values.length != 10000) {
            throw new AssertionError("expected 10000 values but got " + values.length);
        }
        for (int i = 0; i < values.length; i++) {
            double v = values[i];
            if (Double.isNaN(v) || v < 0.0 || v >= 1.0) {
                throw new AssertionError("value out of [0.0, 1.0) at index " + i + ": " + v);
            }
        }
    }

    static void testDoublesLimitProducesExactCount() {
        long n = ThreadLocalRandom.current().doubles().limit(257).count();
        if (n != 257L) {
            throw new AssertionError("expected count 257 but got " + n);
        }
    }

    static void testDoublesCountOnLimitedStream() {
        double[] values = ThreadLocalRandom.current().doubles().limit(0).toArray();
        if (values.length != 0) {
            throw new AssertionError("expected empty array but got length " + values.length);
        }
    }
}
