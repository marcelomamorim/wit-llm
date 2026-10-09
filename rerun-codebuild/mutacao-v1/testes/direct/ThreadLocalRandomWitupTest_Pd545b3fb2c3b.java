/* @test    @summary Test for ThreadLocalRandom.doubles() method    @run main ThreadLocalRandomWitupTest_Pd545b3fb2c3b */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class ThreadLocalRandomWitupTest_Pd545b3fb2c3b {

    public static void main(String[] args) {
        testDoublesMethod();
    }

    private static void testDoublesMethod() {
        DoubleStream ds = ThreadLocalRandom.current().doubles();
        if (ds == null) {
            throw new AssertionError("doubles() returned null");
        }
        // Collect a small number of values to verify stream produces values
        double[] values = ds.limit(10).toArray();
        if (values.length != 10) {
            throw new AssertionError("Expected 10 values, got " + values.length);
        }
        for (double v : values) {
            if (Double.isNaN(v) || Double.isInfinite(v)) {
                throw new AssertionError("Stream produced NaN or Infinite value: " + v);
            }
        }
        System.out.println("PASS: doubles() produces 10 finite double values.");
    }
}
