/* @test    @summary Test java.util.Objects.requireNonNullElse with non-null obj and null defaultObj    @run main ObjectsWitupTest */

import java.util.Objects;

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testRequireNonNullElseWithNonNullObj();
        testRequireNonNullElseWithNullObj();
        testRequireNonNullElseWithBothNull();
    }

    static void testRequireNonNullElseWithNonNullObj() {
        String obj = "value";
        String defaultObj = "default";
        String result = Objects.requireNonNullElse(obj, defaultObj);
        if (!"value".equals(result)) {
            throw new AssertionError("Expected 'value', but got: " + result);
        }
    }

    static void testRequireNonNullElseWithNullObj() {
        String obj = null;
        String defaultObj = "default";
        String result = Objects.requireNonNullElse(obj, defaultObj);
        if (!"default".equals(result)) {
            throw new AssertionError("Expected 'default', but got: " + result);
        }
    }

    static void testRequireNonNullElseWithBothNull() {
        String obj = null;
        String defaultObj = null;
        try {
            Objects.requireNonNullElse(obj, defaultObj);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
