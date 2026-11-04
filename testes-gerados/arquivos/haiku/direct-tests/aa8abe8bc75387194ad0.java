/*
 * @test
 * @summary Behavioral checks for Optional.map: null mapper rejection, empty short-circuit, present mapping and null result handling
 * @run main OptionalWitupTest
 */

import java.util.Optional;

public class OptionalWitupTest {

    private static int failures = 0;

    public static void main(String[] args) {
        testNullMapperOnPresentThrowsNpe();
        testNullMapperOnEmptyThrowsNpe();
        testEmptyDoesNotInvokeMapper();
        testPresentMapsValue();
        testMapperReturningNullYieldsEmpty();
        testMapChangesType();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static void testNullMapperOnPresentThrowsNpe() {
        boolean thrown = false;
        try {
            Optional.of("a").map(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "map(null) on present Optional should throw NullPointerException");
    }

    private static void testNullMapperOnEmptyThrowsNpe() {
        boolean thrown = false;
        try {
            Optional.<String>empty().map(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "map(null) on empty Optional should throw NullPointerException");
    }

    private static void testEmptyDoesNotInvokeMapper() {
        final int[] calls = {0};
        Optional<String> result = Optional.<String>empty().map(s -> {
            calls[0]++;
            return s + "x";
        });
        check(!result.isPresent(), "map on empty Optional should return empty");
        check(calls[0] == 0, "mapper must not be invoked for empty Optional");
    }

    private static void testPresentMapsValue() {
        Optional<Integer> result = Optional.of(3).map(x -> x * 2);
        check(result.isPresent(), "map on present Optional should be present");
        check(result.get() == 6, "map should apply mapper to value, expected 6");
    }

    private static void testMapperReturningNullYieldsEmpty() {
        Optional<String> result = Optional.of("x").map(s -> null);
        check(!result.isPresent(), "mapper returning null should yield empty Optional");
        check(result.orElse("default").equals("default"), "orElse should return default after null mapping");
    }

    private static void testMapChangesType() {
        Optional<Integer> result = Optional.of("hello").map(String::length);
        check(result.isPresent(), "map to different type should be present");
        check(result.get() == 5, "String length of hello should be 5");
    }
}
