/*
 * @test
 * @summary Behavioral tests for Optional.orElseThrow(Supplier)
 * @run main OptionalWitupTest
 */

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

public class OptionalWitupTest {

    public static void main(String[] args) throws Exception {
        testPresentReturnsValue();
        testPresentDoesNotInvokeSupplier();
        testEmptyThrowsSuppliedException();
        testEmptyInvokesSupplierExactlyOnce();
        testEmptyPropagatesCheckedException();
    }

    static void testPresentReturnsValue() {
        String v = Optional.of("abc").orElseThrow(() -> new IllegalStateException("unexpected"));
        check("abc".equals(v), "expected present value 'abc' but got " + v);
    }

    static void testPresentDoesNotInvokeSupplier() {
        final int[] calls = {0};
        Supplier<RuntimeException> s = () -> {
            calls[0]++;
            return new IllegalStateException("should not be used");
        };
        Integer v = Optional.of(Integer.valueOf(7)).orElseThrow(s);
        check(v.intValue() == 7, "expected 7 but got " + v);
        check(calls[0] == 0, "supplier must not be invoked when value is present, calls=" + calls[0]);
    }

    static void testEmptyThrowsSuppliedException() {
        boolean thrown = false;
        try {
            Optional.<String>empty().orElseThrow(() -> new IllegalStateException("missing"));
        } catch (IllegalStateException e) {
            thrown = true;
            check("missing".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "expected IllegalStateException for empty Optional");
    }

    static void testEmptyInvokesSupplierExactlyOnce() {
        final int[] calls = {0};
        boolean thrown = false;
        try {
            Optional.<String>empty().orElseThrow(() -> {
                calls[0]++;
                return new IllegalArgumentException("absent");
            });
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for empty Optional");
        check(calls[0] == 1, "supplier must be invoked exactly once, calls=" + calls[0]);
    }

    static void testEmptyPropagatesCheckedException() {
        boolean thrown = false;
        try {
            Optional.<String>empty().orElseThrow(() -> new IOException("io-absent"));
        } catch (IOException e) {
            thrown = true;
            check("io-absent".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "expected IOException for empty Optional");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
