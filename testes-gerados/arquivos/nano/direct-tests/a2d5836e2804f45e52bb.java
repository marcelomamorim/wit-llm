/* @test    @summary Test java.util.Objects.requireNonNull with non-null object    @run main ObjectsWitupTest */

import java.util.Objects;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testRequireNonNullWithObject();
        testRequireNonNullWithNull();
    }

    static void testRequireNonNullWithObject() {
        String obj = "test";
        String result = Objects.requireNonNull(obj);
        if (result != obj) {
            throw new AssertionError("Expected same object to be returned");
        }
    }

    static void testRequireNonNullWithNull() {
        boolean threw = false;
        try {
            Objects.requireNonNull(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected NullPointerException to be thrown");
        }
    }
}
