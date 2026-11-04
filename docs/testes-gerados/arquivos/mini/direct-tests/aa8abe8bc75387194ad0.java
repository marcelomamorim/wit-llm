/*
 * @test
 * @summary Test Optional.map(Function) behavior
 * @run main java.util.OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Function;
import java.util.Objects;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testMapWithNullMapper();
        testMapOnEmptyOptional();
        testMapOnPresentOptionalReturnsNonNull();
        testMapOnPresentOptionalReturnsNull();
    }

    static void testMapWithNullMapper() {
        Optional<String> opt = Optional.of("test");
        try {
            opt.map(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testMapOnEmptyOptional() {
        Optional<String> empty = Optional.empty();
        Optional<String> result = empty.map(new Function<String, String>() {
            public String apply(String s) {
                throw new AssertionError("Should not be called on empty Optional");
            }
        });
        if (result == null) {
            throw new AssertionError("map() on empty Optional must not return null");
        }
        if (result.isPresent()) {
            throw new AssertionError("map() on empty Optional must return empty Optional");
        }
    }

    static void testMapOnPresentOptionalReturnsNonNull() {
        Optional<String> opt = Optional.of("hello");
        Optional<Integer> mapped = opt.map(new Function<String, Integer>() {
            public Integer apply(String s) {
                return s.length();
            }
        });
        if (!mapped.isPresent()) {
            throw new AssertionError("map() on present Optional with non-null result must be present");
        }
        if (!mapped.get().equals(5)) {
            throw new AssertionError("map() result incorrect, expected 5 but got " + mapped.get());
        }
    }

    static void testMapOnPresentOptionalReturnsNull() {
        Optional<String> opt = Optional.of("hello");
        Optional<String> mapped = opt.map(new Function<String, String>() {
            public String apply(String s) {
                return null;
            }
        });
        if (mapped == null) {
            throw new AssertionError("map() must not return null even if mapper returns null");
        }
        if (mapped.isPresent()) {
            throw new AssertionError("map() must return empty Optional if mapper returns null");
        }
    }
}
