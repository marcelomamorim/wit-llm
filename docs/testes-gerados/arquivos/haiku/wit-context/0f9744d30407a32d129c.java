/*
 * @test
 * @summary Behavioral tests for System.clearProperty(String key) using only public System APIs
 * @run main SystemWitupTest
 */

public class SystemWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testClearExistingPropertyReturnsOldValue() {
        String key = "witup.test.clear.existing";
        System.setProperty(key, "value1");
        try {
            String previous = System.clearProperty(key);
            check("value1".equals(previous), "clearProperty should return previous value, got " + previous);
            check(System.getProperty(key) == null, "property should be removed after clearProperty");
        } finally {
            System.clearProperty(key);
        }
    }

    private static void testClearAbsentPropertyReturnsNull() {
        String key = "witup.test.clear.absent";
        System.clearProperty(key);
        String result = System.clearProperty(key);
        check(result == null, "clearProperty on absent key should return null, got " + result);
    }

    private static void testClearTwiceSecondReturnsNull() {
        String key = "witup.test.clear.twice";
        System.setProperty(key, "v");
        check("v".equals(System.clearProperty(key)), "first clear should return value");
        check(System.clearProperty(key) == null, "second clear should return null");
    }

    private static void testNullKeyThrowsNullPointerException() {
        boolean thrown = false;
        try {
            System.clearProperty(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "clearProperty(null) should throw NullPointerException");
    }

    private static void testEmptyKeyThrowsIllegalArgumentException() {
        boolean thrown = false;
        try {
            System.clearProperty("");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "clearProperty(\"\") should throw IllegalArgumentException");
    }

    public static void main(String[] args) {
        testClearExistingPropertyReturnsOldValue();
        testClearAbsentPropertyReturnsNull();
        testClearTwiceSecondReturnsNull();
        testNullKeyThrowsNullPointerException();
        testEmptyKeyThrowsIllegalArgumentException();
    }
}
