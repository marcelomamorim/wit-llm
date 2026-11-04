/*
 * @test
 * @summary Verify Optional.map behavior: NPE on null mapper, empty propagation, and null-result mapping to empty
 * @run main OptionalWitupTest
 */

import java.util.Optional;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testEmptyOptionalReturnsEmptyWithoutInvokingMapper();
        testPresentValueIsMapped();
        testMapperReturningNullYieldsEmpty();
    }

    static void testNullMapperThrowsNPE() {
        Optional<String> present = Optional.of("a");
        Optional<String> empty = Optional.empty();
        try {
            present.map(null);
            throw new AssertionError("Expected NullPointerException for present optional");
        } catch (NullPointerException expected) {
        }
        try {
            empty.map(null);
            throw new AssertionError("Expected NullPointerException for empty optional");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptyOptionalReturnsEmptyWithoutInvokingMapper() {
        final boolean[] called = {false};
        Optional<String> result = Optional.<String>empty().map(s -> {
            called[0] = true;
            return s + "x";
        });
        if (result.isPresent()) {
            throw new AssertionError("Expected empty result");
        }
        if (called[0]) {
            throw new AssertionError("Mapper must not be invoked for empty Optional");
        }
    }

    static void testPresentValueIsMapped() {
        Optional<Integer> result = Optional.of("abc").map(String::length);
        if (!result.isPresent() || result.get().intValue() != 3) {
            throw new AssertionError("Expected mapped value 3, got " + result);
        }
    }

    static void testMapperReturningNullYieldsEmpty() {
        Optional<String> result = Optional.of("abc").map(s -> null);
        if (result.isPresent()) {
            throw new AssertionError("Mapper returning null must yield empty Optional");
        }
    }
}
