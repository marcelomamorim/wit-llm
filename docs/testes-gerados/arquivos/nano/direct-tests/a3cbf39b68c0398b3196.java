/*
 * @test
 * @summary Test for LongStream.generate method to ensure it produces a stream with expected values.
 * @run main LongStreamWitupTest
 */

import java.util.stream.LongStream;
import java.util.function.LongSupplier;

public class LongStreamWitupTest {

    public static void main(String[] args) {
        testGenerateWithConstant();
        testGenerateWithCounter();
    }

    static void testGenerateWithConstant() {
        LongSupplier supplier = new LongSupplier() {
            private long count = 0;
            @Override
            public long getAsLong() {
                return 42L;
            }
        };
        LongStream stream = LongStream.generate(supplier);
        long[] result = stream.limit(5).toArray();
        for (long val : result) {
            if (val != 42L) {
                throw new AssertionError("Expected 42, but got " + val);
            }
        }
    }

    static void testGenerateWithCounter() {
        LongSupplier counter = new LongSupplier() {
            private long current = 0;
            @Override
            public long getAsLong() {
                return current++;
            }
        };
        LongStream stream = LongStream.generate(counter);
        long[] result = stream.limit(5).toArray();
        long[] expected = {0L, 1L, 2L, 3L, 4L};
        if (!java.util.Arrays.equals(result, expected)) {
            throw new AssertionError("Expected " + java.util.Arrays.toString(expected) + ", but got " + java.util.Arrays.toString(result));
        }
    }
}
