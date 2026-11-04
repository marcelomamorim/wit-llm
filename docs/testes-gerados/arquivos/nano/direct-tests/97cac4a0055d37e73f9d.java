/* @test    @summary Test SplittableRandom.doubles(double, double) for correct range and exception handling    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testDoublesWithinRange();
        testDoublesInvalidRange();
    }

    static void testDoublesWithinRange() {
        SplittableRandom sr = new SplittableRandom(12345L);
        double origin = -10.0;
        double bound = 10.0;
        DoubleStream ds = sr.doubles(origin, bound);
        boolean allWithinRange = ds.allMatch(d -> d >= origin && d < bound);
        if (!allWithinRange) {
            throw new AssertionError("Doubles stream contains value outside range") ;
        }
        // Consume stream to avoid resource warnings
        ds.close();
    }

    static void testDoublesInvalidRange() {
        SplittableRandom sr = new SplittableRandom(54321L);
        double origin = 5.0;
        double bound = -5.0;
        boolean exceptionThrown = false;
        try {
            sr.doubles(origin, bound);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid range") ;
        }
    }
}
