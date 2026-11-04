/*
 * @test
 * @summary Test java.util.Objects.requireNonNull(T obj, Supplier<String> messageSupplier) for null and non-null inputs
 * @run main ObjectsWitupTest
 */

import java.util.Objects;
import java.util.function.Supplier;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testRequireNonNullWithNonNullObject();
        testRequireNonNullWithNullObjectAndNullMessageSupplier();
        testRequireNonNullWithNullObjectAndNonNullMessageSupplier();
    }

    private static void testRequireNonNullWithNonNullObject() {
        String input = "test";
        Supplier<String> messageSupplier = new Supplier<String>() {
            public String get() {
                return "should not be used";
            }
        };
        String result = Objects.requireNonNull(input, messageSupplier);
        if (result != input) {
            throw new AssertionError("requireNonNull should return the same non-null object");
        }
    }

    private static void testRequireNonNullWithNullObjectAndNullMessageSupplier() {
        try {
            Objects.requireNonNull(null, null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            if (e.getMessage() != null) {
                throw new AssertionError("Expected null message in NullPointerException");
            }
        }
    }

    private static void testRequireNonNullWithNullObjectAndNonNullMessageSupplier() {
        final String expectedMessage = "custom message";
        Supplier<String> messageSupplier = new Supplier<String>() {
            public String get() {
                return expectedMessage;
            }
        };
        try {
            Objects.requireNonNull(null, messageSupplier);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            if (!expectedMessage.equals(e.getMessage())) {
                throw new AssertionError("Expected message: '" + expectedMessage + "' but got: '" + e.getMessage() + "'");
            }
        }
    }
}
