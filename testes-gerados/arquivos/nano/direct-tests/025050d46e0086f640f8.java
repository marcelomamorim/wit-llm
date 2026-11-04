/* @test    @summary Test the doubles() method of SplittableRandom for correct stream generation    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testDoublesMethod();
    }

    public static void testDoublesMethod() {
        SplittableRandom sr = new SplittableRandom(12345L);
        DoubleStream stream = sr.doubles();
        double[] values = stream.limit(10).toArray();
        double[] expected = {0.41661987254534116, 0.010169169457068361, 0.8252065092537432, 0.2986398551995928, 0.3684116894884757, 0.19366134904507426, 0.5660105261283184, 0.16168782392927105, 0.12426902387871682, 0.4324011382682816};
        for (int i = 0; i < values.length; i++) {
            if (Math.abs(values[i] - expected[i]) > 1e-15) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + expected[i] + " but got " + values[i]);
            }
        }
        System.out.println("doubles() method test passed.");
    }
}
