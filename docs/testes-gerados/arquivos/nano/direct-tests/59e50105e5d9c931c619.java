/* @test    @summary Test Random.doubles with valid and invalid ranges    @run main RandomWitupTest */

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest {

    public static void main(String[] args) {
        testDoublesValidRange();
        testDoublesInvalidRange();
    }

    static void testDoublesValidRange() {
        Random rand = new Random(12345L);
        double origin = 1.0;
        double bound = 5.0;
        DoubleStream stream = rand.doubles(origin, bound);
        double[] values = stream.limit(10).toArray();
        for (double v : values) {
            assert v >= origin && v < bound : "Value out of range: " + v;
        }
    }

    static void testDoublesInvalidRange() {
        Random rand = new Random(54321L);
        try {
            rand.doubles(10.0, 5.0).limit(1).toArray();
            assert false : "Expected IllegalArgumentException for invalid range";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
