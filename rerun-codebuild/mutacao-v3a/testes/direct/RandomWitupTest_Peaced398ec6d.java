/* @test    @summary Test for Random.doubles() method to ensure it returns a stream of doubles.    @run main RandomWitupTest_Peaced398ec6d */

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest_Peaced398ec6d {

    public static void main(String[] args) {
        testDoublesStreamContainsValues();
    }

    public static void testDoublesStreamContainsValues() {
        Random random = new Random(12345L); // Fixed seed for deterministic behavior
        DoubleStream stream = random.doubles();
        double[] values = stream.limit(10).toArray();
        // Expected values computed with the same seed and algorithm
        double[] expected = {
            0.41661987254534116,
            0.010169170680943920,
            0.8252065092537432,
            0.2986398551995928,
            0.3684116894884757,
            0.1936613490450739,
            0.5660104704910356,
            0.161687823929271,
            0.12426688428353092,
            0.43276706790505337
        };
        for (int i = 0; i < values.length; i++) {
            assert Math.abs(values[i] - expected[i]) < 1e-14 : "Mismatch at index " + i + ": expected " + expected[i] + ", got " + values[i];
        }
    }
}
