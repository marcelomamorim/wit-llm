/*
 * @test
 * @summary Tests for java.util.SplittableRandom.ints(long)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testIntsWithPositiveSize();
        testIntsWithZeroSize();
        testIntsWithNegativeSize();
    }

    static void testIntsWithPositiveSize() {
        SplittableRandom sr = new SplittableRandom();
        long size = 10L;
        IntStream stream = sr.ints(size);
        int[] values = stream.toArray();
        assert values.length == size : "Expected stream length to be " + size;
        for (int v : values) {
            assert v >= Integer.MIN_VALUE && v <= Integer.MAX_VALUE : "Value out of int range";
        }
    }

    static void testIntsWithZeroSize() {
        SplittableRandom sr = new SplittableRandom();
        long size = 0L;
        IntStream stream = sr.ints(size);
        int[] values = stream.toArray();
        assert values.length == 0 : "Expected empty stream for size 0";
    }

    static void testIntsWithNegativeSize() {
        SplittableRandom sr = new SplittableRandom();
        long size = -5L;
        try {
            IntStream stream = sr.ints(size);
            stream.toArray();
            assert false : "Expected IllegalArgumentException for negative size";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
