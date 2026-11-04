/*
 * @test
 * @summary Verifies OptionalDouble.orElseThrow(Supplier) returns the value when present and throws the supplied exception when empty
 * @run main OptionalDoubleWitupTest
 */

import java.io.IOException;
import java.util.OptionalDouble;
import java.util.function.Supplier;

public class OptionalDoubleWitupTest {

    public static void main(String[] args) throws Exception {
        testPresentValueReturnsValue();
        testPresentValueDoesNotInvokeSupplier();
        testPresentNaNReturnsNaN();
        testEmptyThrowsSuppliedRuntimeException();
        testEmptyThrowsSuppliedCheckedException();
        testEmptyInvokesSupplier();
    }

    static void testPresentValueReturnsValue() {
        double r = OptionalDouble.of(7.25).orElseThrow(() -> new IllegalStateException("unexpected"));
        check(r == 7.25, "expected 7.25 but was " + r);
    }

    static void testPresentValueDoesNotInvokeSupplier() {
        boolean[] called = {false};
        Supplier<RuntimeException> s = () -> {
            called[0] = true;
            return new RuntimeException("should not be used");
        };
        double r = OptionalDouble.of(-2.5).orElseThrow(s);
        check(r == -2.5, "expected -2.5 but was " + r);
        check(!called[0], "supplier must not be invoked when value is present");
    }

    static void testPresentNaNReturnsNaN() {
        double r = OptionalDouble.of(Double.NaN).orElseThrow(() -> new IllegalStateException("unexpected"));
        check(Double.isNaN(r), "expected NaN but was " + r);
    }

    static void testEmptyThrowsSuppliedRuntimeException() {
        Supplier<IllegalStateException> s = () -> new IllegalStateException("empty");
        try {
            OptionalDouble.empty().orElseThrow(s);
            throw new AssertionError("expected IllegalStateException");
        } catch (IllegalStateException e) {
            check("empty".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void testEmptyThrowsSuppliedCheckedException() throws Exception {
        try {
            OptionalDouble.empty().orElseThrow(() -> new IOException("io"));
            throw new AssertionError("expected IOException");
        } catch (IOException e) {
            check("io".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void testEmptyInvokesSupplier() {
        boolean[] called = {false};
        Supplier<IllegalStateException> s = () -> {
            called[0] = true;
            return new IllegalStateException("empty");
        };
        try {
            OptionalDouble.empty().orElseThrow(s);
            throw new AssertionError("expected IllegalStateException");
        } catch (IllegalStateException e) {
            check(called[0], "supplier must be invoked when value is absent");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
