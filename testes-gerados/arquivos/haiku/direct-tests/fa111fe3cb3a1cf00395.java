/*
 * @test
 * @summary Tests for java.util.Objects.requireNonNull(T, Supplier)
 * @run main ObjectsWitupTest
 */

import java.util.Objects;
import java.util.function.Supplier;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testNonNullReturnsSameInstanceAndSkipsSupplier();
        testNullWithNullSupplierThrowsNPEWithNullMessage();
        testNullWithSupplierThrowsNPEWithSupplierMessage();
        testNullWithSupplierReturningNullThrowsNPEWithNullMessage();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNonNullReturnsSameInstanceAndSkipsSupplier() {
        int[] calls = {0};
        Supplier<String> supplier = () -> {
            calls[0]++;
            return "msg";
        };
        String value = "value";
        String result = Objects.requireNonNull(value, supplier);
        check(result == value, "expected the same instance to be returned");
        check(calls[0] == 0, "supplier must not be invoked for non-null argument");
    }

    static void testNullWithNullSupplierThrowsNPEWithNullMessage() {
        Supplier<String> nullSupplier = null;
        try {
            Objects.requireNonNull(null, nullSupplier);
        } catch (NullPointerException e) {
            check(e.getMessage() == null, "expected null message when supplier is null");
            return;
        }
        throw new RuntimeException("expected NullPointerException");
    }

    static void testNullWithSupplierThrowsNPEWithSupplierMessage() {
        int[] calls = {0};
        Supplier<String> supplier = () -> {
            calls[0]++;
            return "boom";
        };
        try {
            Objects.requireNonNull(null, supplier);
        } catch (NullPointerException e) {
            check("boom".equals(e.getMessage()), "expected message from supplier");
            check(calls[0] == 1, "supplier must be invoked exactly once");
            return;
        }
        throw new RuntimeException("expected NullPointerException");
    }

    static void testNullWithSupplierReturningNullThrowsNPEWithNullMessage() {
        Supplier<String> supplier = () -> null;
        try {
            Objects.requireNonNull(null, supplier);
        } catch (NullPointerException e) {
            check(e.getMessage() == null, "expected null message when supplier returns null");
            return;
        }
        throw new RuntimeException("expected NullPointerException");
    }
}
