/*
 * @test
 * @summary Verifies ThreadLocalRandom.doubles(origin, bound) rejects invalid ranges and produces values within the requested bounds
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        checkIllegalRange(1.0, 1.0);
        checkIllegalRange(2.0, 1.0);
        checkIllegalRange(Double.NaN, 1.0);
        checkIllegalRange(0.0, Double.NaN);
        checkBounded(-5.0, 5.0);
        checkBounded(0.0, 1e-9);
        checkBounded(-1e300, 1e300);
        checkCountAndLimit(0.0, 1.0, 500);
        System.out.println("PASSED");
    }

    private static void checkIllegalRange(double origin, double bound) {
        try {
            ThreadLocalRandom.current().doubles(origin, bound);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for origin=" + origin + ", bound=" + bound);
    }

    private static void checkBounded(double origin, double bound) {
        double[] values = ThreadLocalRandom.current()
                .doubles(origin, bound)
                .limit(2000)
                .toArray();
        if (values.length != 2000) {
            throw new RuntimeException("Unexpected length: " + values.length);
        }
        for (double v : values) {
            if (!(v >= origin && v < bound)) {
                throw new RuntimeException("Value out of range: " + v);
            }
        }
    }

    private static void checkCountAndLimit(double origin, double bound, int n) {
        DoubleStream s = ThreadLocalRandom.current().doubles(origin, bound).limit(n);
        long count = s.count();
        if (count != n) {
            throw new RuntimeException("Expected count " + n + " but was " + count);
        }
    }
}
