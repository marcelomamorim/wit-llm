/*
 * @test
 * @summary Test for DoubleStream.generate(DoubleSupplier s) method, focusing on null input handling.
 * @run main DoubleStreamWitupTest
 */

import java.util.stream.DoubleStream;
import java.util.function.DoubleSupplier;

public class DoubleStreamWitupTest {

    public static void main(String[] args) {
        testGenerateWithNullSupplier();
        testGenerateWithValidSupplier();
    }

    private static void testGenerateWithNullSupplier() {
        try {
            DoubleStream.generate(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testGenerateWithValidSupplier() {
        DoubleSupplier supplier = new DoubleSupplier() {
            private int count = 0;
            public double getAsDouble() {
                return count++;
            }
        };
        DoubleStream stream = DoubleStream.generate(supplier);
        try {
            double first = stream.iterator().nextDouble();
            double second = stream.iterator().nextDouble();
            if (first == 0.0 && second == 1.0) {
                System.out.println("Passed: generate produces expected sequence");
            } else {
                System.err.println("Failed: Unexpected sequence values: " + first + ", " + second);
            }
        } catch (Throwable t) {
            System.err.println("Failed: Exception during stream consumption: " + t);
        }
    }
}
