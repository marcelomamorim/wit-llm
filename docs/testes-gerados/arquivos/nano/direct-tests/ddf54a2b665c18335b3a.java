/* @test    @summary Test Random.doubles(long streamSize) for various stream sizes    @run main RandomWitupTest */

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest {

    public static void main(String[] args) {
        testDoublesWithPositiveSize();
        testDoublesWithZeroSize();
        testDoublesWithNegativeSize();
    }

    static void testDoublesWithPositiveSize() {
        Random rand = new Random(12345L);
        long size = 5L;
        DoubleStream stream = rand.doubles(size);
        double[] array = stream.toArray();
        assert array.length == size : "Expected size 5, got " + array.length;
        for (double d : array) {
            assert d >= 0.0 && d <= Double.MAX_VALUE : "Value out of range: " + d;
        }
    }

    static void testDoublesWithZeroSize() {
        Random rand = new Random(54321L);
        long size = 0L;
        DoubleStream stream = rand.doubles(size);
        double[] array = stream.toArray();
        assert array.length == 0 : "Expected size 0, got " + array.length;
    }

    static void testDoublesWithNegativeSize() {
        Random rand = new Random(67890L);
        long size = -1L;
        try {
            rand.doubles(size);
            assert false : "Expected IllegalArgumentException for negative size";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("BadSize") || e.getMessage() == null : "Unexpected exception message";
        }
    }
}
