/* @test    @summary Test doubles method of ThreadLocalRandom for valid and invalid ranges    @run main ThreadLocalRandomWitupTest */

import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testDoublesValidRange();
        testDoublesInvalidRange();
    }

    static void testDoublesValidRange() {
        double origin = 1.0;
        double bound = 2.0;
        DoubleStream ds = java.util.concurrent.ThreadLocalRandom.current().doubles(origin, bound);
        double[] values = ds.limit(10).toArray();
        for (double v : values) {
            assert v >= origin && v < bound : "Value out of range: " + v;
        }
    }

    static void testDoublesInvalidRange() {
        boolean exceptionThrown = false;
        try {
            java.util.concurrent.ThreadLocalRandom.current().doubles(5.0, 3.0).limit(1).toArray();
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for invalid range";
    }
}
