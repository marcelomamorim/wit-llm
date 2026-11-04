/*
 * @test
 * @summary Test for Stream.generate(Supplier) method.
 * @run main StreamWitupTest
 */

import java.util.stream.Stream;
import java.util.function.Supplier;

public class StreamWitupTest {

    public static void main(String[] args) {
        testGenerateWithConstantSupplier();
        testGenerateWithNullSupplier();
        testGenerateWithCustomSupplier();
    }

    static void testGenerateWithConstantSupplier() {
        Supplier<Integer> supplier = () -> 42;
        Stream<Integer> stream = Stream.generate(supplier);
        Integer first = stream.findFirst().orElse(null);
        assert first != null && first == 42 : "Expected first element to be 42";
    }

    static void testGenerateWithNullSupplier() {
        try {
            Stream.generate(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testGenerateWithCustomSupplier() {
        Supplier<String> supplier = new Supplier<String>() {
            int count = 0;
            public String get() {
                return "value" + (count++);
            }
        };
        Stream<String> stream = Stream.generate(supplier);
        String first = stream.findFirst().orElse(null);
        String second = stream.skip(1).findFirst().orElse(null);
        assert first != null && first.startsWith("value") : "First element should start with 'value'";
        assert second != null && second.startsWith("value") : "Second element should start with 'value'";
        assert !first.equals(second) : "First and second elements should differ";
    }
}
