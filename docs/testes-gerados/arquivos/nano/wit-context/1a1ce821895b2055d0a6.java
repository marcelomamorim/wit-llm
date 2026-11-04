/*
 * @test
 * @summary Test for ThreadLocalRandom.doubles(long streamSize)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testDoublesWithNegativeSize();
        testDoublesWithZeroSize();
        testDoublesWithPositiveSize();
    }

    private static void testDoublesWithNegativeSize() {
        try {
            ThreadLocalRandom.current().doubles(-1L);
            System.out.println("Failed: Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown for negative size");
        }
    }

    private static void testDoublesWithZeroSize() {
        DoubleStream ds = ThreadLocalRandom.current().doubles(0L);
        if (ds.count() == 0) {
            System.out.println("Passed: Zero size returns empty stream");
        } else {
            System.out.println("Failed: Zero size did not return empty stream");
        }
        ds.close();
    }

    private static void testDoublesWithPositiveSize() {
        long size = 5L;
        DoubleStream ds = ThreadLocalRandom.current().doubles(size);
        long count = ds.count();
        if (count == size) {
            System.out.println("Passed: Stream size matches requested size");
        } else {
            System.out.println("Failed: Stream size mismatch. Expected: " + size + ", Got: " + count);
        }
        ds.close();
    }
}
