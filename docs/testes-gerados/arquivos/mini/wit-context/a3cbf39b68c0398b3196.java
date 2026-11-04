/*
 * @test
 * @summary Test LongStream.generate(LongSupplier) for null argument and normal usage
 * @run main LongStreamWitupTest
 */

import java.util.stream.LongStream;
import java.util.function.LongSupplier;
import java.util.Objects;

public class LongStreamWitupTest {
    public static void main(String[] args) {
        testGenerateWithNullSupplier();
        testGenerateWithValidSupplier();
        System.out.println("All tests passed.");
    }

    private static void testGenerateWithNullSupplier() {
        try {
            LongStream.generate(null);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGenerateWithValidSupplier() {
        final long[] values = {10L, 20L, 30L};
        LongSupplier supplier = new LongSupplier() {
            private int index = 0;
            public long getAsLong() {
                if (index < values.length) {
                    return values[index++];
                } else {
                    return -1L;
                }
            }
        };

        LongStream stream = LongStream.generate(supplier);
        // Collect first 3 elements to verify supplier is used
        long[] collected = stream.limit(3).toArray();

        if (collected.length != 3) {
            throw new AssertionError("Expected 3 elements, got " + collected.length);
        }
        for (int i = 0; i < collected.length; i++) {
            if (collected[i] != values[i]) {
                throw new AssertionError("Element at index " + i + " expected " + values[i] + " but got " + collected[i]);
            }
        }
    }
}
