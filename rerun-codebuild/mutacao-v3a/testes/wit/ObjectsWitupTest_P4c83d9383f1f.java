/*
 * @test
 * @summary Tests for java.util.Objects.requireNonNull
 * @run main ObjectsWitupTest_P4c83d9383f1f
 */

import java.util.Objects;

public class ObjectsWitupTest_P4c83d9383f1f {

    public static void main(String[] args) {
        testRequireNonNullWithNull();
        testRequireNonNullWithNonNull();
        testRequireNonNullWithNullMessageSupplier();
    }

    private static void testRequireNonNullWithNull() {
        boolean exceptionThrown = false;
        try {
            Objects.requireNonNull(null, () -> "message");
        } catch (NullPointerException e) {
            exceptionThrown = true;
            if (e.getMessage() != null) {
                System.out.println("Caught expected NullPointerException with message.");
            } else {
                System.out.println("NullPointerException thrown but message is null.");
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException was not thrown.");
        }
    }

    private static void testRequireNonNullWithNonNull() {
        String testString = "test";
        String result = Objects.requireNonNull(testString, () -> "message");
        if (result != testString) {
            throw new AssertionError("requireNonNull did not return the original object.");
        }
    }

    private static void testRequireNonNullWithNullMessageSupplier() {
        boolean exceptionThrown = false;
        try {
            Objects.requireNonNull(null, null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
            if (e.getMessage() != null) {
                System.out.println("Caught expected NullPointerException with null message.");
            } else {
                System.out.println("NullPointerException thrown but message is null.");
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException was not thrown.");
        }
    }
}
