/*
 * @test
 * @summary Test for Random.doubles(long) method, including edge cases.
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.DoubleStream;

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
        double[] values = stream.toArray();
        assert values.length == size : "Expected length " + size + " but got " + values.length;
        for (double v : values) {
            assert v >= 0.0 && v <= Double.MAX_VALUE : "Value out of range: " + v;
        }
    }

    static void testDoublesWithZeroSize() {
        Random rand = new Random(54321L);
        long size = 0L;
        DoubleStream stream = rand.doubles(size);
        double[] values = stream.toArray();
        assert values.length == 0 : "Expected empty array for size 0";
    }

    static void testDoublesWithNegativeSize() {
        Random rand = new Random(67890L);
        long size = -1L;
        try {
            rand.doubles(size);
            assert false : "Expected IllegalArgumentException for negative size";
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
