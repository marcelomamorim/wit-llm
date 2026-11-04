/*
 * @test
 * @summary Test IntStream.generate(IntSupplier) for null input and normal behavior
 * @run main IntStreamWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntSupplier;
import java.util.Objects;

public class IntStreamWitupTest {
    public static void main(String[] args) {
        testGenerateNullSupplier();
        testGenerateNormalSupplier();
    }

    private static void testGenerateNullSupplier() {
        try {
            IntStream.generate(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGenerateNormalSupplier() {
        final int[] values = {1, 2, 3};
        final int[] index = {0};
        IntSupplier supplier = new IntSupplier() {
            public int getAsInt() {
                int val = values[index[0] % values.length];
                index[0]++;
                return val;
            }
        };

        IntStream stream = IntStream.generate(supplier);
        // Take first 5 elements and verify they match expected cycling pattern
        int[] expected = {1, 2, 3, 1, 2};
        int i = 0;
        for (int v : (int[]) stream.limit(5).toArray()) {
            if (v != expected[i]) {
                throw new Error("Unexpected value at index " + i + ": expected " + expected[i] + ", got " + v);
            }
            i++;
        }
    }
}
