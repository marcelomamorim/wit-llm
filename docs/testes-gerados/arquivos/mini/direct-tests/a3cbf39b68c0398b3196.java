/*
 * @test
 * @summary Test LongStream.generate(LongSupplier) for basic functionality and null check
 * @run main LongStreamWitupTest
 */

import java.util.stream.LongStream;
import java.util.function.LongSupplier;
import java.util.Objects;

public class LongStreamWitupTest {
    public static void main(String[] args) {
        testGenerateProducesInfiniteStream();
        testGenerateRejectsNull();
    }

    static void testGenerateProducesInfiniteStream() {
        final long[] values = new long[3];
        LongSupplier supplier = new LongSupplier() {
            private long current = 1;
            public long getAsLong() {
                return current++;
            }
        };
        LongStream stream = LongStream.generate(supplier);
        // Collect first 3 elements to verify sequence
        long[] collected = stream.limit(3).toArray();
        for (int i = 0; i < 3; i++) {
            if (collected[i] != i + 1) {
                throw new AssertionError("Expected " + (i + 1) + " but got " + collected[i]);
            }
        }
    }

    static void testGenerateRejectsNull() {
        try {
            LongStream.generate(null);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
