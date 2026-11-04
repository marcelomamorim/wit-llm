/*
 * @test
 * @summary Verifies OptionalDouble.orElseThrow returns the value when present and throws the supplied exception when empty
 * @run main OptionalDoubleWitupTest
 */

import java.io.IOException;
import java.util.OptionalDouble;

public class OptionalDoubleWitupTest {

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testPresentReturnsValue() {
        double v = OptionalDouble.of(3.5).orElseThrow(() -> new IllegalStateException("unused"));
        if (v != 3.5) {
            fail("expected 3.5 but got " + v);
        }
    }

    static void testPresentNaNReturnsNaN() {
        double v = OptionalDouble.of(Double.NaN).orElseThrow(() -> new IllegalStateException("unused"));
        if (!Double.isNaN(v)) {
            fail("expected NaN but got " + v);
        }
    }

    static void testPresentNegativeZeroPreserved() {
        double v = OptionalDouble.of(-0.0).orElseThrow(() -> new IllegalStateException("unused"));
        if (v != 0.0 || 1.0 / v != Double.NEGATIVE_INFINITY) {
            fail("expected -0.0 but got " + v);
        }
    }

    static void testEmptyThrowsSuppliedException() {
        try {
            OptionalDouble.empty().orElseThrow(() -> new IllegalStateException("none"));
            fail("no exception thrown for empty OptionalDouble");
        } catch (IllegalStateException e) {
            if (!"none".equals(e.getMessage())) {
                fail("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testEmptyWithCheckedException() {
        try {
            OptionalDouble.empty().orElseThrow(() -> new IOException("io"));
            fail("no exception thrown for empty OptionalDouble");
        } catch (IOException e) {
            if (!"io".equals(e.getMessage())) {
                fail("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testSupplierInvokedOnlyWhenEmpty() {
        int[] calls = {0};
        OptionalDouble.of(1.0).orElseThrow(() -> {
            calls[0]++;
            return new IllegalStateException("unused");
        });
        if (calls[0] != 0) {
            fail("supplier invoked for present value: " + calls[0]);
        }
        try {
            OptionalDouble.empty().orElseThrow(() -> {
                calls[0]++;
                return new IllegalStateException("none");
            });
            fail("no exception thrown for empty OptionalDouble");
        } catch (IllegalStateException e) {
            if (calls[0] != 1) {
                fail("supplier should be invoked once, count=" + calls[0]);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        testPresentReturnsValue();
        testPresentNaNReturnsNaN();
        testPresentNegativeZeroPreserved();
        testEmptyThrowsSuppliedException();
        testEmptyWithCheckedException();
        testSupplierInvokedOnlyWhenEmpty();
    }
}
