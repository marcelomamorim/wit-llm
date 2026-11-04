/*
 * @test
 * @summary Witup-derived behavioral tests for Optional.orElseThrow(Supplier)
 * @run main OptionalWitupTest
 */

import java.io.IOException;
import java.util.Optional;

public class OptionalWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testPresentValueIsReturned();
        testSupplierNotInvokedWhenPresent();
        testEmptyThrowsSupplierException();
        testEmptyThrowsSupplierExceptionType();
        testEmptyInvokesSupplierExactlyOnce();
        testEmptyThrowsCheckedException();
    }

    static void testPresentValueIsReturned() {
        String v = Optional.of("x").orElseThrow(() -> new IllegalStateException("m"));
        check("x".equals(v), "expected value x but got " + v);
    }

    static void testSupplierNotInvokedWhenPresent() {
        int[] calls = new int[1];
        Integer v = Optional.of(5).orElseThrow(() -> {
            calls[0]++;
            return new IllegalStateException("should not be used");
        });
        check(v.intValue() == 5, "expected 5 but got " + v);
        check(calls[0] == 0, "supplier invoked " + calls[0] + " times for present value");
    }

    static void testEmptyThrowsSupplierException() {
        boolean thrown = false;
        try {
            Optional.empty().orElseThrow(() -> new IllegalStateException("missing"));
        } catch (IllegalStateException e) {
            thrown = true;
            check("missing".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "expected IllegalStateException for empty Optional");
    }

    static void testEmptyThrowsSupplierExceptionType() {
        boolean thrown = false;
        try {
            Optional.<String>empty().orElseThrow(() -> new IllegalArgumentException("arg"));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for empty Optional");
    }

    static void testEmptyInvokesSupplierExactlyOnce() {
        int[] calls = new int[1];
        try {
            Optional.empty().orElseThrow(() -> {
                calls[0]++;
                return new IllegalStateException("once");
            });
        } catch (IllegalStateException e) {
            // expected
        }
        check(calls[0] == 1, "supplier invoked " + calls[0] + " times for empty Optional");
    }

    static void testEmptyThrowsCheckedException() {
        boolean thrown = false;
        try {
            Optional.<String>empty().orElseThrow(() -> new IOException("io"));
        } catch (IOException e) {
            thrown = true;
            check("io".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
        check(thrown, "expected IOException for empty Optional");
    }
}
