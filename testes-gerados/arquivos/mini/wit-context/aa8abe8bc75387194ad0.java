/*
 * @test
 * @summary Test Optional.map(Function) for null mapper and empty Optional
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Function;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testMapWithNullMapper();
        testMapOnEmptyOptional();
        testMapOnNonEmptyOptional();
    }

    private static void testMapWithNullMapper() {
        Optional<String> opt = Optional.of("test");
        try {
            opt.map(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testMapOnEmptyOptional() {
        Optional<String> emptyOpt = Optional.empty();
        Optional<String> result = emptyOpt.map(new Function<String, String>() {
            public String apply(String s) {
                throw new AssertionError("Should not be called on empty Optional");
            }
        });
        if (result == null) {
            throw new AssertionError("Result of map on empty Optional should not be null");
        }
        if (result.isPresent()) {
            throw new AssertionError("Result of map on empty Optional should be empty");
        }
    }

    private static void testMapOnNonEmptyOptional() {
        Optional<String> opt = Optional.of("hello");
        Optional<Integer> mapped = opt.map(new Function<String, Integer>() {
            public Integer apply(String s) {
                return s.length();
            }
        });
        if (!mapped.isPresent()) {
            throw new AssertionError("Mapped Optional should be present");
        }
        if (mapped.get() != 5) {
            throw new AssertionError("Mapped value should be 5 but was " + mapped.get());
        }
    }
}
