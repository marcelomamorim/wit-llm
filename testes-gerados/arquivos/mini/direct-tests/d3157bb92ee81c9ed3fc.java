/*
 * @test
 * @summary Test java.util.Objects.requireNonNull(T obj, String message) method
 * @run main ObjectsWitupTest
 */

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testRequireNonNullReturnsObject();
        testRequireNonNullThrowsOnNull();
    }

    private static void testRequireNonNullReturnsObject() {
        String s = "test";
        String result = java.util.Objects.requireNonNull(s, "should not be null");
        if (result != s) {
            throw new AssertionError("requireNonNull did not return the same object");
        }
    }

    private static void testRequireNonNullThrowsOnNull() {
        String message = "null object";
        try {
            java.util.Objects.requireNonNull(null, message);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            if (!message.equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch: expected '" + message + "' but got '" + e.getMessage() + "'");
            }
        }
    }
}
