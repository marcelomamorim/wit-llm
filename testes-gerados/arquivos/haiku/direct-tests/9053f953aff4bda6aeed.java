/*
 * @test
 * @summary Behavior of Optional.flatMap: null mapper, empty receiver, present receiver, empty and null mapper results
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Function;

public class OptionalWitupTest {
    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testEmptyDoesNotInvokeMapper();
        testPresentValueIsMapped();
        testMapperReturningEmptyYieldsEmpty();
        testMapperReturningNullThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullMapperThrowsNPE() {
        try {
            Optional.of("a").flatMap(null);
            throw new RuntimeException("expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyDoesNotInvokeMapper() {
        final int[] calls = new int[1];
        Optional<String> empty = Optional.empty();
        Function<String, Optional<String>> f = s -> {
            calls[0]++;
            return Optional.of(s);
        };
        Optional<String> result = empty.flatMap(f);
        check(!result.isPresent(), "flatMap on empty must return empty");
        check(calls[0] == 0, "mapper must not be invoked for empty Optional");
    }

    static void testPresentValueIsMapped() {
        Optional<Integer> result = Optional.of("abc").flatMap(s -> Optional.of(s.length()));
        check(result.isPresent(), "result must be present");
        check(result.get() == 3, "expected length 3 but was " + result.get());
    }

    static void testMapperReturningEmptyYieldsEmpty() {
        Optional<String> result = Optional.of("x").flatMap(s -> Optional.<String>empty());
        check(!result.isPresent(), "mapper returning empty must yield empty result");
    }

    static void testMapperReturningNullThrowsNPE() {
        Function<String, Optional<String>> f = s -> null;
        try {
            Optional.of("x").flatMap(f);
            throw new RuntimeException("expected NullPointerException when mapper returns null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
