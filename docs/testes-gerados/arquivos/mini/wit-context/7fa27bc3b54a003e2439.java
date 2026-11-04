/*
 * @test
 * @summary Test DoubleStream.generate(DoubleSupplier) for null argument and normal usage
 * @run main DoubleStreamWitupTest
 */

import java.util.stream.DoubleStream;
import java.util.function.DoubleSupplier;
import java.util.Objects;

public class DoubleStreamWitupTest {
    public static void main(String[] args) {
        testGenerateWithNullSupplier();
        testGenerateWithValidSupplier();
    }

    private static void testGenerateWithNullSupplier() {
        try {
            DoubleStream.generate(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGenerateWithValidSupplier() {
        DoubleSupplier supplier = new DoubleSupplier() {
            private double value = 1.0;
            @Override
            public double getAsDouble() {
                return value++;
            }
        };

        DoubleStream stream = DoubleStream.generate(supplier);
        // Take first 3 elements and verify they are as expected
        double[] results = stream.limit(3).toArray();
        if (results.length != 3) {
            throw new RuntimeException("Expected 3 elements, got " + results.length);
        }
        if (results[0] != 1.0 || results[1] != 2.0 || results[2] != 3.0) {
            throw new RuntimeException("Unexpected elements in generated stream: " + java.util.Arrays.toString(results));
        }
    }
}
