/*
 * @test
 * @summary Verifies observable behavior of Objects.requireNonNullElse
 * @run main ObjectsWitupTest
 */

public class ObjectsWitupTest {

    public static void main(String[] args) {
        testReturnsObjWhenNonNull();
        testReturnsDefaultWhenObjNull();
        testThrowsNpeWithMessageWhenBothNull();
        testNullDefaultIgnoredWhenObjNonNull();
    }

    static void testReturnsObjWhenNonNull() {
        String obj = "value";
        String def = "default";
        String result = java.util.Objects.requireNonNullElse(obj, def);
        check(result == obj, "expected same instance as obj when obj is non-null");
    }

    static void testReturnsDefaultWhenObjNull() {
        String def = "default";
        String result = java.util.Objects.requireNonNullElse(null, def);
        check(result == def, "expected defaultObj when obj is null");
    }

    static void testThrowsNpeWithMessageWhenBothNull() {
        boolean thrown = false;
        try {
            java.util.Objects.requireNonNullElse(null, null);
        } catch (NullPointerException e) {
            thrown = true;
            check("defaultObj".equals(e.getMessage()),
                  "expected NPE message 'defaultObj' but was: " + e.getMessage());
        }
        check(thrown, "expected NullPointerException when obj and defaultObj are both null");
    }

    static void testNullDefaultIgnoredWhenObjNonNull() {
        Integer obj = Integer.valueOf(42);
        Integer result = java.util.Objects.requireNonNullElse(obj, null);
        check(result == obj, "expected obj without evaluating null defaultObj check");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
