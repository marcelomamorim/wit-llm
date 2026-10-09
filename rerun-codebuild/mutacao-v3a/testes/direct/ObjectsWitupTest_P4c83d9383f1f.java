/* @test    @summary Test java.util.Objects.requireNonNull method for null and non-null inputs    @run main ObjectsWitupTest_P4c83d9383f1f */

import java.util.Objects;
import java.util.function.Supplier;

public class ObjectsWitupTest_P4c83d9383f1f {

    public static void main(String[] args) {
        testRequireNonNullWithNonNullObject();
        testRequireNonNullWithNullObject();
        testRequireNonNullWithNullSupplier();
        testRequireNonNullWithNonNullSupplier();
    }

    static void testRequireNonNullWithNonNullObject() {
        Object obj = new Object();
        Object result = Objects.requireNonNull(obj, () -> "Should not throw for non-null object");
        if (result != obj) {
            throw new AssertionError("Expected same object to be returned");
        }
    }

    static void testRequireNonNullWithNullObject() {
        Object obj = null;
        boolean exceptionThrown = false;
        try {
            Objects.requireNonNull(obj, () -> "Object is null");
        } catch (NullPointerException e) {
            exceptionThrown = true;
            if (e.getMessage() != null && !e.getMessage().equals("Object is null")) {
                throw new AssertionError("Exception message mismatch");
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException to be thrown for null object");
        }
    }

    static void testRequireNonNullWithNullSupplier() {
        Object obj = new Object();
        boolean exceptionThrown = false;
        try {
            Objects.requireNonNull(obj, (Supplier<String>) null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
            if (e.getMessage() != null) {
                throw new AssertionError("Expected null message when supplier is null");
            }
        }
        if (exceptionThrown) {
            throw new AssertionError("NullPointerException was thrown unexpectedly");
        }
    }

    static void testRequireNonNullWithNonNullSupplier() {
        Object obj = null;
        boolean exceptionThrown = false;
        try {
            Objects.requireNonNull(obj, () -> "Null object with supplier");
        } catch (NullPointerException e) {
            exceptionThrown = true;
            if (e.getMessage() == null || !e.getMessage().equals("Null object with supplier")) {
                throw new AssertionError("Exception message mismatch");
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException to be thrown");
        }
    }
}
