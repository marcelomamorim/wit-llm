/* @test    @summary Test the doubles() method of ThreadLocalRandom for correct stream generation    @run main ThreadLocalRandomWitupTest */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testDoublesMethod();
    }

    public static void testDoublesMethod() {
        DoubleStream stream = ThreadLocalRandom.current().doubles();
        double[] values = stream.limit(10).toArray();
        assert values.length == 10 : "Stream should produce 10 elements";
        for (double v : values) {
            assert v >= 0.0 && v <= Double.MAX_VALUE : "Value out of expected range";
        }
        System.out.println("doubles() method test passed.");
    }
}
