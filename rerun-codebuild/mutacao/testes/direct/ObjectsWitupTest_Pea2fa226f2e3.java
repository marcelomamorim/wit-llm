/* @test    @summary Test java.util.Objects.requireNonNull with non-null object    @run main ObjectsWitupTest_Pea2fa226f2e3 */

import java.util.Objects;

public class ObjectsWitupTest_Pea2fa226f2e3 {
    public static void main(String[] args) {
        testRequireNonNullWithObject();
        testRequireNonNullWithNullObject();
    }

    static void testRequireNonNullWithObject() {
        String obj = "test";
        String result = Objects.requireNonNull(obj, () -> "Object should not be null");
        if (!result.equals(obj)) {
            throw new AssertionError("Expected object to be returned unchanged");
        }
    }

    static void testRequireNonNullWithNullObject() {
        try {
            Objects.requireNonNull(null, () -> "Object is null");
            throw new AssertionError("Expected NullPointerException to be thrown");
        } catch (NullPointerException e) {
            if (!"Object is null".equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch", e);
            }
        }
    }
}
