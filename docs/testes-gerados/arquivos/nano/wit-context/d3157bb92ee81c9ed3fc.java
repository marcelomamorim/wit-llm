/* @test    @summary Test for java.util.Objects.requireNonNull method to verify null input throws NullPointerException    @run main ObjectsWitupTest */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testRequireNonNullWithNull();
        testRequireNonNullWithNonNull();
    }

    private static void testRequireNonNullWithNull() {
        String message = "Input cannot be null";
        try {
            Objects.requireNonNull(null, message);
            System.err.println("Failed: Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            if (!e.getMessage().equals(message)) {
                System.err.println("Failed: Exception message does not match expected");
            } else {
                System.out.println("Passed: Null input correctly throws NullPointerException with message")
            }
        }
    }

    private static void testRequireNonNullWithNonNull() {
        String input = "valid";
        String result = Objects.requireNonNull(input, "message");
        if (result != input) {
            System.err.println("Failed: requireNonNull did not return the input object");
        } else {
            System.out.println("Passed: Non-null input returns the same object")
        }
    }
}
