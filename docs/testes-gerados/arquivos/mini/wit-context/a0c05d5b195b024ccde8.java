/*
 * @test
 * @summary Test Optional.or(Supplier) method behavior and null checks
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.Objects;
import java.util.function.Supplier;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testOrWithNullSupplier();
        testOrWithPresentOptional();
        testOrWithEmptyOptionalAndNonNullSupplier();
    }

    private static void testOrWithNullSupplier() {
        Optional<String> opt = Optional.empty();
        try {
            opt.or(null);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testOrWithPresentOptional() {
        Optional<String> opt = Optional.of("value");
        Optional<String> result = opt.or(new Supplier<Optional<String>>() {
            public Optional<String> get() {
                throw new AssertionError("Supplier.get() should not be called when Optional is present");
            }
        });
        if (!result.isPresent()) {
            throw new AssertionError("Expected present Optional to be returned");
        }
        if (!"value".equals(result.get())) {
            throw new AssertionError("Expected value 'value', got: " + result.get());
        }
    }

    private static void testOrWithEmptyOptionalAndNonNullSupplier() {
        Optional<String> opt = Optional.empty();
        Optional<String> alt = Optional.of("alternative");
        Optional<String> result = opt.or(new Supplier<Optional<String>>() {
            public Optional<String> get() {
                return alt;
            }
        });
        if (!result.isPresent()) {
            throw new AssertionError("Expected present Optional from supplier");
        }
        if (!"alternative".equals(result.get())) {
            throw new AssertionError("Expected value 'alternative', got: " + result.get());
        }
    }
}
