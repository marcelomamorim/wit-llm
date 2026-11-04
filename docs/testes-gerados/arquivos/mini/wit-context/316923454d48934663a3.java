/*
 * @test
 * @summary Test java.util.Objects.requireNonNullElse behavior including null handling
 * @run main ObjectsWitupTest
 */

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testRequireNonNullElseNonNullObj();
        testRequireNonNullElseNullObjNonNullDefault();
        testRequireNonNullElseNullObjNullDefault();
    }

    private static void testRequireNonNullElseNonNullObj() {
        String obj = "value";
        String defaultObj = "default";
        String result = java.util.Objects.requireNonNullElse(obj, defaultObj);
        if (!result.equals(obj)) {
            throw new AssertionError("Expected obj to be returned when non-null");
        }
    }

    private static void testRequireNonNullElseNullObjNonNullDefault() {
        String obj = null;
        String defaultObj = "default";
        String result = java.util.Objects.requireNonNullElse(obj, defaultObj);
        if (!result.equals(defaultObj)) {
            throw new AssertionError("Expected defaultObj to be returned when obj is null and defaultObj non-null");
        }
    }

    private static void testRequireNonNullElseNullObjNullDefault() {
        String obj = null;
        String defaultObj = null;
        try {
            java.util.Objects.requireNonNullElse(obj, defaultObj);
            throw new AssertionError("Expected NullPointerException when both obj and defaultObj are null");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }
}
