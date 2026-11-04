/* @test    @summary Test for Random.doubles(double, double) method to verify behavior with valid and invalid inputs    @run main RandomWitupTest */

import java.util.Random;
import java.util.stream.DoubleStream;

public class RandomWitupTest {

    public static void main(String[] args) {
        testDoublesValidRange();
        testDoublesInvalidRange();
    }

    static void testDoublesValidRange() {
        Random rand = new Random(12345L);
        double origin = 1.0;
        double bound = 5.0;
        DoubleStream ds = rand.doubles(origin, bound);
        double[] values = ds.limit(10).toArray();
        boolean allInRange = true;
        for (double v : values) {
            if (v < origin || v >= bound) {
                allInRange = false;
                break;
            }
        }
        if (!allInRange) {
            throw new AssertionError("Doubles generated outside the specified range") ;
        }
    }

    static void testDoublesInvalidRange() {
        Random rand = new Random(54321L);
        double origin = 5.0;
        double bound = 1.0;
        try {
            rand.doubles(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException not thrown") ;
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
