/*
 * @test
 * @summary Test java.util.Objects.requireNonNull(T obj, String message) for null and non-null inputs
 * @run main java.util.ObjectsWitupTest
 */

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testRequireNonNullWithNonNull();
        testRequireNonNullWithNull();
        System.out.println("All tests passed.");
    }

    private static void testRequireNonNullWithNonNull() {
        String input = "test";
        String result = java.util.Objects.requireNonNull(input, "should not be null");
        if (result != input) {
            throw new AssertionError("requireNonNull should return the same non-null object");
        }
    }

    private static void testRequireNonNullWithNull() {
        String message = "input is null";
        try {
            java.util.Objects.requireNonNull(null, message);
            throw new AssertionError("requireNonNull did not throw NullPointerException for null input");
        } catch (NullPointerException e) {
            if (!message.equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: expected '" + message + "' but got '" + e.getMessage() + "'");
            }
        }
    }
}
