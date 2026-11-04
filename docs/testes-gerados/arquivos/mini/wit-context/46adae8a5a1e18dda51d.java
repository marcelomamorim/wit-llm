/*
 * @test
 * @summary Test java.util.Random.longs(long) for valid and invalid streamSize
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.LongStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testLongsWithValidSize();
        testLongsWithZeroSize();
        testLongsWithNegativeSize();
    }

    private static void testLongsWithValidSize() {
        Random rnd = new Random(12345L);
        LongStream stream = rnd.longs(5L);
        long count = stream.count();
        if (count != 5L) {
            throw new AssertionError("Expected stream size 5 but got " + count);
        }
    }

    private static void testLongsWithZeroSize() {
        Random rnd = new Random(12345L);
        LongStream stream = rnd.longs(0L);
        long count = stream.count();
        if (count != 0L) {
            throw new AssertionError("Expected stream size 0 but got " + count);
        }
    }

    private static void testLongsWithNegativeSize() {
        Random rnd = new Random(12345L);
        try {
            rnd.longs(-1L);
            throw new AssertionError("Expected IllegalArgumentException for negative streamSize");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
