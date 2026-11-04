/*
 * @test
 * @summary Witup-derived behavioural checks for OptionalLong.orElseThrow(Supplier)
 * @run main OptionalLongWitupTest
 */
import java.util.OptionalLong;

public class OptionalLongWitupTest {

    static final class CustomException extends Exception {
        private static final long serialVersionUID = 1L;

        CustomException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testPresentReturnsValue();
        testEmptyThrowsSupplierException();
        testSupplierNotInvokedWhenPresent();
        testEmptyWithNullSupplierResultThrowsNPE();
        testZeroValueIsPresent();
    }

    static void testPresentReturnsValue() throws Exception {
        long v = OptionalLong.of(42L).orElseThrow(() -> new CustomException("unused"));
        if (v != 42L) {
            throw new AssertionError("expected 42 but was " + v);
        }
    }

    static void testEmptyThrowsSupplierException() {
        try {
            OptionalLong.empty().orElseThrow(() -> new CustomException("no value"));
            throw new AssertionError("expected CustomException");
        } catch (CustomException e) {
            if (!"no value".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testSupplierNotInvokedWhenPresent() throws Exception {
        final boolean[] invoked = {false};
        long v = OptionalLong.of(7L).orElseThrow(() -> {
            invoked[0] = true;
            return new CustomException("should not be created");
        });
        if (v != 7L) {
            throw new AssertionError("expected 7 but was " + v);
        }
        if (invoked[0]) {
            throw new AssertionError("supplier was invoked for a present value");
        }
    }

    static void testEmptyWithNullSupplierResultThrowsNPE() {
        try {
            OptionalLong.empty().orElseThrow(() -> null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected: throw null raises NPE
        } catch (Throwable t) {
            throw new AssertionError("unexpected exception: " + t, t);
        }
    }

    static void testZeroValueIsPresent() throws Exception {
        long v = OptionalLong.of(0L).orElseThrow(() -> new CustomException("unused"));
        if (v != 0L) {
            throw new AssertionError("expected 0 but was " + v);
        }
    }
}
