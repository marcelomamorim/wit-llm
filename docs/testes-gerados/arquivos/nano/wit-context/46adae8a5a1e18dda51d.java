/*
 * @test
 * @summary Test for Random.longs(long streamSize) method, including edge cases.
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.LongStream;

public class RandomWitupTest {

    public static void main(String[] args) {
        testLongsWithPositiveSize();
        testLongsWithZeroSize();
        testLongsWithNegativeSize();
    }

    static void testLongsWithPositiveSize() {
        Random rand = new Random(12345L);
        long size = 10L;
        LongStream stream = rand.longs(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size: " + size + ", but got: " + count);
        }
    }

    static void testLongsWithZeroSize() {
        Random rand = new Random(54321L);
        long size = 0L;
        LongStream stream = rand.longs(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size: " + size + ", but got: " + count);
        }
    }

    static void testLongsWithNegativeSize() {
        Random rand = new Random(67890L);
        long size = -5L;
        try {
            rand.longs(size);
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }
}
