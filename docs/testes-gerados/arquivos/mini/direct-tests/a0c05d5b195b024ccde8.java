/*
 * @test
 * @summary Test Optional.or(Supplier) method behavior
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.Objects;
import java.util.function.Supplier;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testOrReturnsThisWhenPresent();
        testOrReturnsSupplierValueWhenEmpty();
        testOrThrowsNPEWhenSupplierIsNull();
        testOrThrowsNPEWhenSupplierReturnsNull();
    }

    private static void testOrReturnsThisWhenPresent() {
        Optional<String> opt = Optional.of("value");
        Optional<String> result = opt.or(new Supplier<Optional<String>>() {
            public Optional<String> get() {
                throw new AssertionError("Supplier.get() should not be called when Optional is present");
            }
        });
        if (!result.isPresent() || !"value".equals(result.get())) {
            throw new AssertionError("or() did not return this Optional when present");
        }
    }

    private static void testOrReturnsSupplierValueWhenEmpty() {
        final Optional<String> supplied = Optional.of("supplied");
        Optional<String> empty = Optional.empty();
        Optional<String> result = empty.or(new Supplier<Optional<String>>() {
            public Optional<String> get() {
                return supplied;
            }
        });
        if (!result.isPresent() || !"supplied".equals(result.get())) {
            throw new AssertionError("or() did not return supplier's Optional when empty");
        }
    }

    private static void testOrThrowsNPEWhenSupplierIsNull() {
        Optional<String> empty = Optional.empty();
        try {
            empty.or(null);
            throw new AssertionError("or() did not throw NullPointerException when supplier is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testOrThrowsNPEWhenSupplierReturnsNull() {
        Optional<String> empty = Optional.empty();
        try {
            empty.or(new Supplier<Optional<String>>() {
                public Optional<String> get() {
                    return null;
                }
            });
            throw new AssertionError("or() did not throw NullPointerException when supplier returns null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
