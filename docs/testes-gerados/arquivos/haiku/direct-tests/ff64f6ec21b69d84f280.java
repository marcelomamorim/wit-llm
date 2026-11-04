/*
 * @test
 * @summary Tests OptionalLong.orElseThrow for present and empty instances
 * @run main OptionalLongWitupTest
 */

import java.io.IOException;
import java.util.OptionalLong;
import java.util.function.Supplier;

public class OptionalLongWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testPresentReturnsValue();
        testPresentDoesNotInvokeSupplier();
        testPresentZeroAndNegativeValues();
        testEmptyThrowsSuppliedRuntimeException();
        testEmptyThrowsSuppliedCheckedException();
        testEmptyWithNullSupplierResultThrowsNpe();
    }

    static void testPresentReturnsValue() {
        OptionalLong present = OptionalLong.of(42L);
        long v = present.orElseThrow(() -> new IllegalStateException("not expected"));
        check(v == 42L, "expected 42 but was " + v);
    }

    static void testPresentDoesNotInvokeSupplier() {
        OptionalLong present = OptionalLong.of(7L);
        final boolean[] invoked = {false};
        Supplier<RuntimeException> supplier = () -> {
            invoked[0] = true;
            return new IllegalStateException("not expected");
        };
        long v = present.orElseThrow(supplier);
        check(v == 7L, "expected 7 but was " + v);
        check(!invoked[0], "supplier must not be invoked when value is present");
    }

    static void testPresentZeroAndNegativeValues() {
        long zero = OptionalLong.of(0L).orElseThrow(() -> new IllegalStateException("not expected"));
        check(zero == 0L, "expected 0 but was " + zero);
        long min = OptionalLong.of(Long.MIN_VALUE).orElseThrow(() -> new IllegalStateException("not expected"));
        check(min == Long.MIN_VALUE, "expected Long.MIN_VALUE but was " + min);
    }

    static void testEmptyThrowsSuppliedRuntimeException() {
        OptionalLong empty = OptionalLong.empty();
        try {
            empty.orElseThrow(() -> new IllegalArgumentException("boom"));
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            check("boom".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void testEmptyThrowsSuppliedCheckedException() {
        OptionalLong empty = OptionalLong.empty();
        try {
            empty.orElseThrow(() -> new IOException("io-failure"));
            throw new RuntimeException("expected IOException");
        } catch (IOException e) {
            check("io-failure".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void testEmptyWithNullSupplierResultThrowsNpe() {
        OptionalLong empty = OptionalLong.empty();
        try {
            empty.orElseThrow(() -> null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected: 'throw null' raises NullPointerException
        }
    }
}
