/* @test    @summary Test for ThreadLocalRandom.doubles() method    @run main ThreadLocalRandomWitupTest_Pb89c91541fbf */

import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class ThreadLocalRandomWitupTest_Pb89c91541fbf {
    public static void main(String[] args) {
        testDoublesMethod();
    }

    public static void testDoublesMethod() {
        DoubleStream stream = java.util.concurrent.ThreadLocalRandom.current().doubles();
        boolean hasElements = false;
        try {
            hasElements = stream.iterator().hasNext();
        } catch (UnsupportedOperationException e) {
            // Expected if stream is infinite; ignore for test
        }
        // Basic test: ensure stream is not null
        if (stream == null) {
            throw new AssertionError("doubles() returned null");
        }
        // Attempt to get at least one element if possible
        try {
            Double firstValue = stream.findFirst().orElse(Double.NaN);
            // Check that returned value is a Double (not null)
            if (firstValue == null) {
                throw new AssertionError("First value is null");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception occurred while testing doubles()", e);
        }
    }
}
