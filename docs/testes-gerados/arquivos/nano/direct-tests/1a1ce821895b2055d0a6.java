/*
 * @test
 * @summary Test ThreadLocalRandom.doubles(long) for correct behavior and exception handling
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testDoublesWithValidSize();
        testDoublesWithNegativeSize();
    }

    static void testDoublesWithValidSize() {
        long size = 5L;
        DoubleStream ds = ThreadLocalRandom.current().doubles(size);
        double[] values = ds.toArray();
        assert values.length == size : "Expected length " + size + " but got " + values.length;
        for (double v : values) {
            assert v >= 0.0 && v <= Double.MAX_VALUE : "Value out of range: " + v;
        }
    }

    static void testDoublesWithNegativeSize() {
        long size = -1L;
        try {
            ThreadLocalRandom.current().doubles(size);
            assert false : "Expected IllegalArgumentException for negative size";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
