/*
 * @test
 * @summary Test IntStream.generate(IntSupplier) for basic functionality and null check
 * @run main IntStreamWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntSupplier;
import java.util.Objects;

public class IntStreamWitupTest {
    public static void main(String[] args) {
        testGenerateProducesExpectedValues();
        testGenerateRejectsNull();
    }

    private static void testGenerateProducesExpectedValues() {
        final int[] values = {10, 20, 30};
        IntSupplier supplier = new IntSupplier() {
            private int index = 0;
            public int getAsInt() {
                int val = values[index];
                index = (index + 1) % values.length;
                return val;
            }
        };

        IntStream stream = IntStream.generate(supplier);

        // Collect first 6 elements to verify cycling through values
        int[] results = stream.limit(6).toArray();

        int[] expected = {10, 20, 30, 10, 20, 30};

        if (results.length != expected.length) {
            throw new AssertionError("Length mismatch: expected " + expected.length + " but got " + results.length);
        }

        for (int i = 0; i < expected.length; i++) {
            if (results[i] != expected[i]) {
                throw new AssertionError("Value mismatch at index " + i + ": expected " + expected[i] + " but got " + results[i]);
            }
        }
    }

    private static void testGenerateRejectsNull() {
        try {
            IntStream.generate(null);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
