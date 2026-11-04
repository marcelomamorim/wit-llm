/*
 * @test
 * @summary Verifies GetPropertyAction.run() default value, property lookup, and exception behavior for null and empty keys
 * @modules java.base/sun.security.action
 * @run main GetPropertyActionWitupTest
 */
import sun.security.action.GetPropertyAction;

public class GetPropertyActionWitupTest {

    private static final String ABSENT = "witup.gpa.absent.property";
    private static final String PRESENT = "witup.gpa.present.property";

    public static void main(String[] args) {
        System.clearProperty(ABSENT);
        System.clearProperty(PRESENT);

        testAbsentWithoutDefaultReturnsNull();
        testAbsentWithDefaultReturnsDefault();
        testPresentWithoutDefaultReturnsValue();
        testPresentWithDefaultReturnsValueNotDefault();
        testNullKeyThrowsNullPointerException();
        testEmptyKeyThrowsIllegalArgumentException();

        System.clearProperty(ABSENT);
        System.clearProperty(PRESENT);
    }

    private static void testAbsentWithoutDefaultReturnsNull() {
        String result = new GetPropertyAction(ABSENT).run();
        if (result != null) {
            throw new RuntimeException("Expected null for absent property without default, got: " + result);
        }
    }

    private static void testAbsentWithDefaultReturnsDefault() {
        String result = new GetPropertyAction(ABSENT, "fallback").run();
        if (!"fallback".equals(result)) {
            throw new RuntimeException("Expected default value, got: " + result);
        }
    }

    private static void testPresentWithoutDefaultReturnsValue() {
        System.setProperty(PRESENT, "value1");
        try {
            String result = new GetPropertyAction(PRESENT).run();
            if (!"value1".equals(result)) {
                throw new RuntimeException("Expected property value, got: " + result);
            }
        } finally {
            System.clearProperty(PRESENT);
        }
    }

    private static void testPresentWithDefaultReturnsValueNotDefault() {
        System.setProperty(PRESENT, "value2");
        try {
            String result = new GetPropertyAction(PRESENT, "fallback").run();
            if (!"value2".equals(result)) {
                throw new RuntimeException("Expected property value over default, got: " + result);
            }
        } finally {
            System.clearProperty(PRESENT);
        }
    }

    private static void testNullKeyThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new GetPropertyAction(null).run();
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null key");
        }
    }

    private static void testEmptyKeyThrowsIllegalArgumentException() {
        boolean thrown = false;
        try {
            new GetPropertyAction("").run();
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for empty key");
        }
    }
}
