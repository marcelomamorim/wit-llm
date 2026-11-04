/*
 * @test
 * @summary Test java.util.Objects.requireNonNullElse behavior
 * @run main java.util.ObjectsWitupTest
 */

public class ObjectsWitupTest {
    public static void main(String[] args) {
        testRequireNonNullElseReturnsObjWhenNotNull();
        testRequireNonNullElseReturnsDefaultObjWhenObjNull();
        testRequireNonNullElseThrowsNPEWhenBothNull();
    }

    private static void testRequireNonNullElseReturnsObjWhenNotNull() {
        String obj = "hello";
        String defaultObj = "world";
        String result = java.util.Objects.requireNonNullElse(obj, defaultObj);
        if (!result.equals(obj)) {
            throw new AssertionError("Expected obj to be returned when not null");
        }
    }

    private static void testRequireNonNullElseReturnsDefaultObjWhenObjNull() {
        String obj = null;
        String defaultObj = "default";
        String result = java.util.Objects.requireNonNullElse(obj, defaultObj);
        if (!result.equals(defaultObj)) {
            throw new AssertionError("Expected defaultObj to be returned when obj is null");
        }
    }

    private static void testRequireNonNullElseThrowsNPEWhenBothNull() {
        String obj = null;
        String defaultObj = null;
        try {
            java.util.Objects.requireNonNullElse(obj, defaultObj);
            throw new AssertionError("Expected NullPointerException when both obj and defaultObj are null");
        } catch (NullPointerException npe) {
            // expected
            String msg = npe.getMessage();
            if (msg == null || !msg.equals("defaultObj")) {
                throw new AssertionError("Expected NullPointerException message to be 'defaultObj'");
            }
        }
    }
}
