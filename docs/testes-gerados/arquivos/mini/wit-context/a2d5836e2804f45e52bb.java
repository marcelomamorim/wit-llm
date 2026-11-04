/*
 * @test
 * @summary Test java.util.Objects.requireNonNull(T obj) for null and non-null inputs
 * @run main java.util.ObjectsWitupTest
 */

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testRequireNonNullWithNonNull();
        testRequireNonNullWithNull();
    }

    private static void testRequireNonNullWithNonNull() {
        String s = "test";
        String result = java.util.Objects.requireNonNull(s);
        if (result != s) {
            throw new AssertionError("requireNonNull should return the same non-null object");
        }
    }

    private static void testRequireNonNullWithNull() {
        try {
            java.util.Objects.requireNonNull(null);
            throw new AssertionError("requireNonNull(null) should throw NullPointerException");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
