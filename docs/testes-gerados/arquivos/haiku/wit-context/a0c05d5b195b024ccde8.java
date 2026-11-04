/*
 * @test
 * @summary Witup-derived behavioral tests for Optional.or(Supplier)
 * @run main OptionalWitupTest
 */

import java.util.Optional;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testNullSupplierThrowsNPE();
        testPresentReturnsSameInstanceAndDoesNotInvokeSupplier();
        testEmptyInvokesSupplierAndReturnsResult();
        testEmptyWithNullSupplierResultThrowsNPE();
    }

    static void testNullSupplierThrowsNPE() {
        Optional<String> opt = Optional.empty();
        try {
            opt.or(null);
            throw new AssertionError("Expected NullPointerException for null supplier");
        } catch (NullPointerException expected) {
        }

        Optional<String> present = Optional.of("x");
        try {
            present.or(null);
            throw new AssertionError("Expected NullPointerException for null supplier on present");
        } catch (NullPointerException expected) {
        }
    }

    static void testPresentReturnsSameInstanceAndDoesNotInvokeSupplier() {
        Optional<String> present = Optional.of("value");
        boolean[] called = {false};
        Optional<String> result = present.or(() -> {
            called[0] = true;
            return Optional.of("other");
        });
        if (result != present) {
            throw new AssertionError("Expected same instance when value is present");
        }
        if (called[0]) {
            throw new AssertionError("Supplier must not be invoked when value is present");
        }
    }

    static void testEmptyInvokesSupplierAndReturnsResult() {
        Optional<String> empty = Optional.empty();
        Optional<String> supplied = Optional.of("fallback");
        Optional<String> result = empty.or(() -> supplied);
        if (result != supplied) {
            throw new AssertionError("Expected supplier result when empty");
        }
        if (!"fallback".equals(result.get())) {
            throw new AssertionError("Unexpected value: " + result.get());
        }
    }

    static void testEmptyWithNullSupplierResultThrowsNPE() {
        Optional<String> empty = Optional.empty();
        try {
            empty.or(() -> null);
            throw new AssertionError("Expected NullPointerException when supplier returns null");
        } catch (NullPointerException expected) {
        }
    }
}
