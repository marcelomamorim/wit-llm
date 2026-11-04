/*
 * @test
 * @summary Test for LongStream.generate(LongSupplier s)
 * @run main LongStreamWitupTest
 */

import java.util.stream.LongStream;
import java.util.function.LongSupplier;

public class LongStreamWitupTest {

    public static void main(String[] args) {
        testGenerateWithNullSupplier();
        testGenerateWithValidSupplier();
    }

    private static void testGenerateWithNullSupplier() {
        try {
            LongStream.generate(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGenerateWithValidSupplier() {
        LongSupplier supplier = new LongSupplier() {
            private long current = 0;
            @Override
            public long getAsLong() {
                return current++;
            }
        };
        LongStream stream = LongStream.generate(supplier);
        long[] result = stream.limit(5).toArray();
        long[] expected = {0, 1, 2, 3, 4};
        if (!java.util.Arrays.equals(result, expected)) {
            System.err.println("Generated stream does not match expected values");
            System.exit(1);
        }
    }
}
