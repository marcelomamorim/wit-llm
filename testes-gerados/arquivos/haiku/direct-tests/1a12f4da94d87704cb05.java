/*
 * @test
 * @summary Verify OptionalInt.orElseThrow returns the value when present and throws the supplied exception when empty
 * @run main OptionalIntWitupTest
 */

import java.util.OptionalInt;
import java.util.function.Supplier;

public class OptionalIntWitupTest {

    static class MarkerException extends Exception {
        MarkerException(String msg) {
            super(msg);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testPresentReturnsValue();
        testEmptyThrowsSuppliedException();
        testSupplierInvokedOnlyWhenEmpty();
        testPresentWithNullSupplierReturnsValue();
        testEmptyWithNullSupplierThrowsNpe();
        testEmptyWithNullReturnedExceptionThrowsNpe();
    }

    static void testPresentReturnsValue() throws Exception {
        int v = OptionalInt.of(42).orElseThrow(() -> new MarkerException("unexpected"));
        check(v == 42, "expected 42 but was " + v);

        int zero = OptionalInt.of(0).orElseThrow(() -> new MarkerException("unexpected"));
        check(zero == 0, "expected 0 but was " + zero);

        int neg = OptionalInt.of(-7).orElseThrow(() -> new MarkerException("unexpected"));
        check(neg == -7, "expected -7 but was " + neg);
    }

    static void testEmptyThrowsSuppliedException() {
        try {
            OptionalInt.empty().orElseThrow(() -> new MarkerException("absent"));
            check(false, "expected MarkerException");
        } catch (MarkerException e) {
            check("absent".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
    }

    static void testSupplierInvokedOnlyWhenEmpty() throws Exception {
        int[] calls = {0};
        OptionalInt.of(5).orElseThrow(() -> {
            calls[0]++;
            return new MarkerException("x");
        });
        check(calls[0] == 0, "supplier invoked for present value: " + calls[0]);

        try {
            OptionalInt.empty().orElseThrow(() -> {
                calls[0]++;
                return new MarkerException("y");
            });
            check(false, "expected MarkerException");
        } catch (MarkerException e) {
            check(calls[0] == 1, "supplier invocations expected 1 but was " + calls[0]);
        }
    }

    static void testPresentWithNullSupplierReturnsValue() throws Exception {
        int v = OptionalInt.of(9).orElseThrow((Supplier<MarkerException>) null);
        check(v == 9, "expected 9 but was " + v);
    }

    static void testEmptyWithNullSupplierThrowsNpe() {
        try {
            OptionalInt.empty().orElseThrow((Supplier<MarkerException>) null);
            check(false, "expected NullPointerException");
        } catch (NullPointerException e) {
            // expected: supplier is null and must be invoked when empty
        } catch (MarkerException e) {
            check(false, "unexpected MarkerException");
        }
    }

    static void testEmptyWithNullReturnedExceptionThrowsNpe() {
        Supplier<MarkerException> s = () -> null;
        try {
            OptionalInt.empty().orElseThrow(s);
            check(false, "expected NullPointerException");
        } catch (NullPointerException e) {
            // expected: throwing null raises NullPointerException
        } catch (MarkerException e) {
            check(false, "unexpected MarkerException");
        }
    }
}
