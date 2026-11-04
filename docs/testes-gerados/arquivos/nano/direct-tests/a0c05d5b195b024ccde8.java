/* @test    @summary Test Optional.or() method for different presence states    @run main OptionalWitupTest */

import java.util.Optional;
import java.util.function.Supplier;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testOrWhenPresent();
        testOrWhenEmpty();
        testOrWithNullSupplier();
    }

    static void testOrWhenPresent() {
        Optional<String> presentOpt = Optional.of("value");
        Supplier<Optional<String>> supplier = () -> {
            throw new AssertionError("Supplier should not be called when Optional is present");
        };
        Optional<String> result = presentOpt.or(supplier);
        if (!result.isPresent() || !result.get().equals("value")) {
            throw new AssertionError("Expected Optional with value 'value', but got: " + result);
        }
    }

    static void testOrWhenEmpty() {
        Optional<String> emptyOpt = Optional.empty();
        Optional<String> fallbackOpt = Optional.of("fallback");
        Supplier<Optional<String>> supplier = () -> {
            return fallbackOpt;
        };
        Optional<String> result = emptyOpt.or(supplier);
        if (!result.isPresent() || !result.get().equals("fallback")) {
            throw new AssertionError("Expected Optional with value 'fallback', but got: " + result);
        }
    }

    static void testOrWithNullSupplier() {
        Optional<String> emptyOpt = Optional.empty();
        try {
            emptyOpt.or(null);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
