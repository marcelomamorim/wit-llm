/*
 * @test
 * @summary Test java.util.Optional.flatMap behavior
 * @run main java.util.OptionalWitupTest
 */

import java.util.Optional;
import java.util.Objects;
import java.util.function.Function;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testFlatMapWithNullMapper();
        testFlatMapOnEmptyOptionalReturnsEmpty();
        testFlatMapOnPresentReturnsMappedOptional();
        testFlatMapMapperReturnsNullThrowsNPE();
    }

    static void testFlatMapWithNullMapper() {
        Optional<String> opt = Optional.of("test");
        try {
            opt.flatMap(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFlatMapOnEmptyOptionalReturnsEmpty() {
        Optional<String> empty = Optional.empty();
        Optional<String> result = empty.flatMap(new Function<String, Optional<String>>() {
            public Optional<String> apply(String s) {
                throw new AssertionError("Should not be called on empty Optional");
            }
        });
        if (result == null) {
            throw new AssertionError("Result should not be null");
        }
        if (result.isPresent()) {
            throw new AssertionError("Result should be empty");
        }
    }

    static void testFlatMapOnPresentReturnsMappedOptional() {
        Optional<String> opt = Optional.of("foo");
        Optional<Integer> result = opt.flatMap(new Function<String, Optional<Integer>>() {
            public Optional<Integer> apply(String s) {
                if ("foo".equals(s)) {
                    return Optional.of(42);
                } else {
                    return Optional.empty();
                }
            }
        });
        if (!result.isPresent()) {
            throw new AssertionError("Result should be present");
        }
        if (!result.get().equals(42)) {
            throw new AssertionError("Result value should be 42");
        }
    }

    static void testFlatMapMapperReturnsNullThrowsNPE() {
        Optional<String> opt = Optional.of("bar");
        try {
            opt.flatMap(new Function<String, Optional<String>>() {
                public Optional<String> apply(String s) {
                    return null; // deliberately return null to test NPE
                }
            });
            throw new AssertionError("Expected NullPointerException when mapper returns null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
