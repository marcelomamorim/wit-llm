/* @test    @summary Test Random.ints(long streamSize) for correct stream size and exception handling    @run main RandomWitupTest */

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest {

    public static void main(String[] args) {
        testIntsWithPositiveSize();
        testIntsWithZeroSize();
        testIntsWithNegativeSize();
    }

    static void testIntsWithPositiveSize() {
        Random rand = new Random(12345L);
        long size = 10L;
        IntStream stream = rand.ints(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size: " + size + ", but got: " + count);
        }
    }

    static void testIntsWithZeroSize() {
        Random rand = new Random(54321L);
        long size = 0L;
        IntStream stream = rand.ints(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size: " + size + ", but got: " + count);
        }
    }

    static void testIntsWithNegativeSize() {
        Random rand = new Random(67890L);
        long size = -5L;
        try {
            rand.ints(size);
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
