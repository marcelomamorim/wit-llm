/* @test    @summary Tests for java.util.Random.ints(long) method handling of invalid input.    @run main RandomWitupTest */

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest {

    public static void main(String[] args) {
        testIntsNegativeStreamSize();
        testIntsZeroStreamSize();
        testIntsPositiveStreamSize();
    }

    private static void testIntsNegativeStreamSize() {
        Random rand = new Random();
        try {
            rand.ints(-1L);
            System.err.println("Failed: Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException thrown for negative streamSize");
        }
    }

    private static void testIntsZeroStreamSize() {
        Random rand = new Random();
        IntStream stream = rand.ints(0L);
        if (stream.count() == 0) {
            System.out.println("Passed: Zero streamSize produces empty stream");
        } else {
            System.err.println("Failed: Zero streamSize did not produce empty stream");
        }
        stream.close();
    }

    private static void testIntsPositiveStreamSize() {
        Random rand = new Random();
        long size = 5L;
        IntStream stream = rand.ints(size);
        try {
            long count = stream.count();
            if (count == size) {
                System.out.println("Passed: Stream of size " + size + " produced correct number of elements");
            } else {
                System.err.println("Failed: Stream of size " + size + " produced " + count + " elements");
            }
        } finally {
            stream.close();
        }
    }
}
