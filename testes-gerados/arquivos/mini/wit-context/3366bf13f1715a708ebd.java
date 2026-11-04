/*
 * @test
 * @summary Test java.util.Random.ints(long) for valid and invalid streamSize
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testIntsWithValidSize();
        testIntsWithZeroSize();
        testIntsWithNegativeSize();
    }

    private static void testIntsWithValidSize() {
        Random rnd = new Random(12345L);
        long size = 5L;
        IntStream stream = rnd.ints(size);
        int count = (int) stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size " + size + ", but got " + count);
        }
    }

    private static void testIntsWithZeroSize() {
        Random rnd = new Random(12345L);
        long size = 0L;
        IntStream stream = rnd.ints(size);
        int count = (int) stream.count();
        if (count != 0) {
            throw new AssertionError("Expected empty stream for size 0, but got " + count);
        }
    }

    private static void testIntsWithNegativeSize() {
        Random rnd = new Random(12345L);
        long size = -1L;
        try {
            rnd.ints(size);
            throw new AssertionError("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Expected IllegalArgumentException but got " + e);
        }
    }
}
