/*
 * @test
 * @summary Witup-derived tests for Optional.flatMap null handling and observable behavior
 * @run main OptionalWitupTest
 */

import java.util.Optional;
import java.util.function.Function;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testNullMapperWithPresentValue();
        testNullMapperWithEmptyOptional();
        testMapperReturningNullThrowsNpe();
        testEmptyOptionalDoesNotInvokeMapper();
        testPresentValueMappedThroughMapper();
        testMapperReturningEmptyYieldsEmpty();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNullPointerException(Runnable action, String message) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException(message);
    }

    static void testNullMapperWithPresentValue() {
        Optional<String> opt = Optional.of("abc");
        final Function<String, Optional<String>> nullMapper = null;
        expectNullPointerException(
            () -> opt.flatMap(nullMapper),
            "flatMap(null) on present Optional must throw NullPointerException");
    }

    static void testNullMapperWithEmptyOptional() {
        final Optional<String> opt = Optional.empty();
        final Function<String, Optional<String>> nullMapper = null;
        expectNullPointerException(
            () -> opt.flatMap(nullMapper),
            "flatMap(null) on empty Optional must throw NullPointerException");
    }

    static void testMapperReturningNullThrowsNpe() {
        final Optional<String> opt = Optional.of("abc");
        final Function<String, Optional<String>> nullReturning = s -> null;
        expectNullPointerException(
            () -> opt.flatMap(nullReturning),
            "flatMap must throw NullPointerException when mapper returns null");
    }

    static void testEmptyOptionalDoesNotInvokeMapper() {
        final int[] calls = {0};
        final Optional<String> opt = Optional.empty();
        Function<String, Optional<Integer>> mapper = s -> {
            calls[0]++;
            return Optional.of(s.length());
        };
        Optional<Integer> result = opt.flatMap(mapper);
        check(!result.isPresent(), "flatMap on empty Optional must return empty");
        check(calls[0] == 0, "mapper must not be invoked for empty Optional");
    }

    static void testPresentValueMappedThroughMapper() {
        Optional<String> opt = Optional.of("abcd");
        Function<String, Optional<Integer>> mapper = s -> Optional.of(s.length());
        Optional<Integer> result = opt.flatMap(mapper);
        check(result.isPresent(), "flatMap must return present result when mapper returns present");
        check(result.get() == 4, "flatMap must return mapper result");
    }

    static void testMapperReturningEmptyYieldsEmpty() {
        Optional<String> opt = Optional.of("abc");
        Function<String, Optional<Integer>> mapper = s -> Optional.empty();
        Optional<Integer> result = opt.flatMap(mapper);
        check(!result.isPresent(), "flatMap must return empty when mapper returns empty");
    }
}
