/*
 * @test
 * @summary Test java.util.Objects.requireNonNull(T obj, Supplier<String> messageSupplier)
 * @run main java.util.ObjectsWitupTest
 */

import java.util.Objects;
import java.util.function.Supplier;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testRequireNonNullReturnsObject();
        testRequireNonNullThrowsWithMessage();
        testRequireNonNullThrowsWithNullMessageSupplier();
    }

    private static void testRequireNonNullReturnsObject() {
        String s = "test";
        String result = Objects.requireNonNull(s, new Supplier<String>() {
            public String get() {
                return "should not be used";
            }
        });
        if (result != s) {
            throw new AssertionError("requireNonNull did not return the input object when non-null");
        }
    }

    private static void testRequireNonNullThrowsWithMessage() {
        final String message = "custom message";
        try {
            Objects.requireNonNull(null, new Supplier<String>() {
                public String get() {
                    return message;
                }
            });
            throw new AssertionError("requireNonNull did not throw NullPointerException when obj is null");
        } catch (NullPointerException e) {
            if (e.getMessage() == null || !e.getMessage().equals(message)) {
                throw new AssertionError("NullPointerException message was incorrect: " + e.getMessage());
            }
        }
    }

    private static void testRequireNonNullThrowsWithNullMessageSupplier() {
        try {
            Objects.requireNonNull(null, null);
            throw new AssertionError("requireNonNull did not throw NullPointerException when obj is null and messageSupplier is null");
        } catch (NullPointerException e) {
            if (e.getMessage() != null) {
                throw new AssertionError("NullPointerException message should be null when messageSupplier is null");
            }
        }
    }
}
