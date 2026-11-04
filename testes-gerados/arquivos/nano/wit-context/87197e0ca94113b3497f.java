/*
 * @test
 * @summary Tests for java.util.stream.Stream.generate(Supplier<? extends T> s)
 * @run main StreamWitupTest
 */

import java.util.stream.Stream;
import java.util.function.Supplier;

public class StreamWitupTest {

    public static void main(String[] args) {
        testGenerateWithNullSupplier();
        testGenerateWithValidSupplier();
    }

    public static void testGenerateWithNullSupplier() {
        try {
            Stream.generate(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testGenerateWithValidSupplier() {
        Supplier<Integer> supplier = () -> 42;
        Stream<Integer> stream = Stream.generate(supplier);
        Integer firstElement = stream.findFirst().orElse(null);
        if (firstElement == null || firstElement != 42) {
            throw new AssertionError("Stream.generate did not produce expected element");
        }
    }
}
