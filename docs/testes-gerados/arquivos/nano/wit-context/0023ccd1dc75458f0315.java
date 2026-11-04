/*
 * @test
 * @summary Test for ThreadLocalRandom.ints(long) method, focusing on exception path for invalid input.
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testIntsWithNegativeStreamSize();
        testIntsWithZeroStreamSize();
        testIntsWithPositiveStreamSize();
    }

    private static void testIntsWithNegativeStreamSize() {
        try {
            ThreadLocalRandom.current().ints(-1L);
            System.err.println("Expected IllegalArgumentException for negative streamSize, but none was thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes.
        }
    }

    private static void testIntsWithZeroStreamSize() {
        try {
            IntStream stream = ThreadLocalRandom.current().ints(0L);
            if (stream == null) {
                System.err.println("ints(0L) returned null.");
                System.exit(1);
            }
            long count = stream.count();
            if (count != 0) {
                System.err.println("ints(0L) did not produce empty stream.");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception for ints(0L): " + e);
            System.exit(1);
        }
    }

    private static void testIntsWithPositiveStreamSize() {
        try {
            IntStream stream = ThreadLocalRandom.current().ints(5L);
            if (stream == null) {
                System.err.println("ints(5L) returned null.");
                System.exit(1);
            }
            long count = stream.count();
            if (count != 5) {
                System.err.println("ints(5L) did not produce stream of size 5.");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception for ints(5L): " + e);
            System.exit(1);
        }
    }
}
