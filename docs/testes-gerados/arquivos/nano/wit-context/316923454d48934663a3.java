/*
 * @test
 * @summary Test for java.util.Objects.requireNonNullElse method
 * @run main ObjectsWitupTest
 */

import java.util.Objects;

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testRequireNonNullElseWithNonNullObject();
        testRequireNonNullElseWithNullObjectAndNullDefault();
        testRequireNonNullElseWithNullObjectAndNonNullDefault();
    }

    private static void testRequireNonNullElseWithNonNullObject() {
        String obj = "test";
        String defaultObj = "default";
        String result = Objects.requireNonNullElse(obj, defaultObj);
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test', but got: " + result);
        }
    }

    private static void testRequireNonNullElseWithNullObjectAndNullDefault() {
        String obj = null;
        String defaultObj = null;
        String result = Objects.requireNonNullElse(obj, defaultObj);
        if (result != null) {
            throw new AssertionError("Expected null, but got: " + result);
        }
    }

    private static void testRequireNonNullElseWithNullObjectAndNonNullDefault() {
        String obj = null;
        String defaultObj = "default";
        String result = Objects.requireNonNullElse(obj, defaultObj);
        if (!"default".equals(result)) {
            throw new AssertionError("Expected 'default', but got: " + result);
        }
    }
}
