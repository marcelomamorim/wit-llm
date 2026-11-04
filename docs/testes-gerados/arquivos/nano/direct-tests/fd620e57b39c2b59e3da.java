/* @test    @summary Test for SplittableRandom.ints(long) method    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testIntsWithPositiveSize();
        testIntsWithZeroSize();
        testIntsWithNegativeSize();
    }

    static void testIntsWithPositiveSize() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long size = 10L;
        IntStream stream = sr.ints(size);
        int[] values = stream.toArray();
        assert values.length == size : "Expected length " + size + " but got " + values.length;
        for (int v : values) {
            assert v >= 0 && v <= Integer.MAX_VALUE : "Value out of expected range: " + v;
        }
    }

    static void testIntsWithZeroSize() {
        SplittableRandom sr = new SplittableRandom(54321L);
        long size = 0L;
        IntStream stream = sr.ints(size);
        int[] values = stream.toArray();
        assert values.length == 0 : "Expected length 0 but got " + values.length;
    }

    static void testIntsWithNegativeSize() {
        SplittableRandom sr = new SplittableRandom(67890L);
        long size = -5L;
        boolean exceptionThrown = false;
        try {
            sr.ints(size).toArray();
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for negative size";
    }
}
