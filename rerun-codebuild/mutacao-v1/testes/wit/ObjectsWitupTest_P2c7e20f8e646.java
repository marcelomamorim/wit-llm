/* @test    @summary Test for Objects.requireNonNull with null input expecting NullPointerException    @run main ObjectsWitupTest_P2c7e20f8e646 */

import java.util.Objects;
import java.util.function.Supplier;

public class ObjectsWitupTest_P2c7e20f8e646 {

    public static void main(String[] args) {
        testRequireNonNullWithNull();
        testRequireNonNullWithNonNull();
    }

    private static void testRequireNonNullWithNull() {
        try {
            Objects.requireNonNull(null, () -> "message");
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }

    private static void testRequireNonNullWithNonNull() {
        String obj = "test";
        String result = Objects.requireNonNull(obj, () -> "message");
        if (result != obj) {
            throw new AssertionError("Returned object does not match input");
        }
    }
}
