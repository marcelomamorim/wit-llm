/* @test    @summary Test java.util.Objects.requireNonNull with non-null object    @run main ObjectsWitupTest */

import java.util.Objects;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testRequireNonNullWithObject();
        testRequireNonNullWithNull();
    }

    static void testRequireNonNullWithObject() {
        String obj = "test";
        String result = Objects.requireNonNull(obj, "Object should not be null");
        if (result == null || !result.equals(obj)) {
            throw new AssertionError("Expected non-null object to be returned");
        }
    }

    static void testRequireNonNullWithNull() {
        boolean exceptionThrown = false;
        try {
            Objects.requireNonNull(null, "Object is null");
        } catch (NullPointerException e) {
            exceptionThrown = true;
            if (!e.getMessage().equals("Object is null")) {
                throw new AssertionError("Exception message mismatch");
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException to be thrown");
        }
    }
}
