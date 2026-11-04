/*
 * @test
 * @summary Test java.util.Optional.flatMap(Function) for null mapper and empty Optional
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Function;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testFlatMapWithNullMapper();
        testFlatMapOnEmptyOptional();
        testFlatMapOnPresentOptional();
    }

    private static void testFlatMapWithNullMapper() {
        Optional<String> opt = Optional.of("test");
        try {
            opt.flatMap(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testFlatMapOnEmptyOptional() {
        Optional<String> emptyOpt = Optional.empty();
        Optional<String> result = emptyOpt.flatMap(new Function<String, Optional<String>>() {
            public Optional<String> apply(String s) {
                throw new AssertionError("Should not be called on empty Optional");
            }
        });
        if (result == null) {
            throw new AssertionError("flatMap on empty Optional must not return null");
        }
        if (result.isPresent()) {
            throw new AssertionError("flatMap on empty Optional must return empty Optional");
        }
    }

    private static void testFlatMapOnPresentOptional() {
        Optional<String> opt = Optional.of("value");
        Optional<Integer> result = opt.flatMap(new Function<String, Optional<Integer>>() {
            public Optional<Integer> apply(String s) {
                if ("value".equals(s)) {
                    return Optional.of(42);
                } else {
                    return Optional.empty();
                }
            }
        });
        if (!result.isPresent()) {
            throw new AssertionError("flatMap on present Optional with non-empty result must be present");
        }
        if (result.get() != 42) {
            throw new AssertionError("flatMap returned wrong value");
        }
    }
}
