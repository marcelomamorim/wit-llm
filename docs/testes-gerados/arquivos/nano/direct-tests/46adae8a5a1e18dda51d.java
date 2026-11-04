/* @test    @summary Test Random.longs(long streamSize) for various stream sizes    @run main RandomWitupTest */

import java.util.Random;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

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
        long[] array = stream.toArray();
        assert array.length == size : "Expected length " + size + " but got " + array.length;
        for (long val : array) {
            assert val >= Long.MIN_VALUE && val <= Long.MAX_VALUE : "Value out of range";
        }
    }

    static void testLongsWithZeroSize() {
        Random rand = new Random(54321L);
        long size = 0L;
        LongStream stream = rand.longs(size);
        long[] array = stream.toArray();
        assert array.length == 0 : "Expected length 0 but got " + array.length;
    }

    static void testLongsWithNegativeSize() {
        Random rand = new Random(67890L);
        long size = -5L;
        try {
            rand.longs(size);
            assert false : "Expected IllegalArgumentException for negative size";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
