/*
 * @test
 * @summary Behavioral tests for Optional.of(T value) through public observable methods
 * @run main OptionalWitupTest
 */

import java.util.Optional;

public class OptionalWitupTest {

    public static void main(String[] args) {
        testOfNonNullIsPresent();
        testOfReturnsSameReference();
        testOfWithIntegerValue();
        testOfNullThrowsNullPointerException();
        testOrElseReturnsWrappedValue();
        testEqualityOfWrappedValues();
        testInequalityOfDifferentValues();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    static void testOfNonNullIsPresent() {
        Optional<String> o = Optional.of("abc");
        check(o.isPresent(), "Optional.of(non-null) must be present");
    }

    static void testOfReturnsSameReference() {
        String s = "value";
        Optional<String> o = Optional.of(s);
        check(o.get() == s, "Optional.of must wrap the same reference");
    }

    static void testOfWithIntegerValue() {
        Optional<Integer> o = Optional.of(0);
        check(o.isPresent(), "Optional.of(0) must be present");
        check(o.get().intValue() == 0, "Optional.of(0).get() must return 0");
    }

    static void testOfNullThrowsNullPointerException() {
        boolean thrown = false;
        try {
            Optional.of(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "Optional.of(null) must throw NullPointerException");
    }

    static void testOrElseReturnsWrappedValue() {
        String s = "wrapped";
        check(Optional.of(s).orElse("other") == s,
              "orElse on present Optional must return wrapped value");
    }

    static void testEqualityOfWrappedValues() {
        check(Optional.of("a").equals(Optional.of("a")),
              "Optionals wrapping equal values must be equal");
    }

    static void testInequalityOfDifferentValues() {
        check(!Optional.of("a").equals(Optional.of("b")),
              "Optionals wrapping different values must not be equal");
    }
}
