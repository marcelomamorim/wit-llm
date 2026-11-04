/*
 * @test
 * @summary Test DoubleStream.generate(DoubleSupplier) for basic functionality and null check
 * @run main DoubleStreamWitupTest
 */

import java.util.stream.DoubleStream;
import java.util.function.DoubleSupplier;
import java.util.Objects;

public class DoubleStreamWitupTest {
    public static void main(String[] args) {
        testGenerateWithValidSupplier();
        testGenerateWithNullSupplier();
    }

    private static void testGenerateWithValidSupplier() {
        final double fixedValue = 3.14d;
        DoubleSupplier supplier = new DoubleSupplier() {
            private int count = 0;
            @Override
            public double getAsDouble() {
                count++;
                return fixedValue;
            }
        };

        DoubleStream stream = DoubleStream.generate(supplier);
        // Take first 5 elements and verify they are equal to fixedValue
        double[] values = stream.limit(5).toArray();
        if (values.length != 5) {
            throw new AssertionError("Expected 5 elements, got " + values.length);
        }
        for (int i = 0; i < values.length; i++) {
            if (values[i] != fixedValue) {
                throw new AssertionError("Element at index " + i + " expected " + fixedValue + " but was " + values[i]);
            }
        }
    }

    private static void testGenerateWithNullSupplier() {
        try {
            DoubleStream.generate(null);
            throw new AssertionError("Expected NullPointerException when supplier is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
