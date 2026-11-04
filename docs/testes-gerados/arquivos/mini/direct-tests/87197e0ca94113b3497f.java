/*
 * @test
 * @summary Test java.util.stream.Stream.generate(Supplier) method
 * @run main StreamWitupTest
 */

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.stream.StreamSpliterators;
import java.util.function.Supplier;

public class StreamWitupTest {
    public static void main(String[] args) {
        testGenerateWithValidSupplier();
        testGenerateWithNullSupplier();
    }

    private static void testGenerateWithValidSupplier() {
        Supplier<String> supplier = new Supplier<String>() {
            private int count = 0;
            public String get() {
                return "val" + (count++);
            }
        };

        Stream<String> stream = Stream.generate(supplier);
        // Check that stream is not null
        if (stream == null) {
            throw new AssertionError("Stream.generate returned null");
        }

        // Check that stream produces expected values
        String[] expected = {"val0", "val1", "val2"};
        Object[] actual = stream.limit(3).toArray();

        for (int i = 0; i < expected.length; i++) {
            if (!Objects.equals(expected[i], actual[i])) {
                throw new AssertionError("Expected " + expected[i] + " but got " + actual[i]);
            }
        }
    }

    private static void testGenerateWithNullSupplier() {
        try {
            Stream.generate(null);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
