/*
 * @test
 * @summary Test Stream.generate(Supplier) for null argument and basic functionality
 * @run main StreamWitupTest
 */

import java.util.stream.Stream;
import java.util.function.Supplier;
import java.util.Objects;

public class StreamWitupTest {
    public static void main(String[] args) {
        testGenerateNullSupplier();
        testGenerateBasicStream();
        System.out.println("All tests passed.");
    }

    private static void testGenerateNullSupplier() {
        try {
            Stream.generate(null);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGenerateBasicStream() {
        Supplier<String> supplier = new Supplier<String>() {
            private int count = 0;
            @Override
            public String get() {
                return "val" + (++count);
            }
        };

        Stream<String> stream = Stream.generate(supplier);
        // Take first 3 elements and verify
        Object[] results = stream.limit(3).toArray();
        if (results.length != 3) {
            throw new AssertionError("Expected 3 elements, got " + results.length);
        }
        if (!"val1".equals(results[0]) || !"val2".equals(results[1]) || !"val3".equals(results[2])) {
            throw new AssertionError("Stream.generate did not produce expected sequence");
        }
    }
}
