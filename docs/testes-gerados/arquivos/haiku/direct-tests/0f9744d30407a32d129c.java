/*
 * @test
 * @summary Verifies System.clearProperty returns the previous value, removes the property and returns null when absent
 * @run main SystemWitupTest
 */
public class SystemWitupTest {

    private static final String KEY = "witup.clearProperty.test.key";

    public static void main(String[] args) {
        testReturnsPreviousValueAndRemoves();
        testAbsentKeyReturnsNull();
        testEmptyValueIsReturned();
        testRepeatedClearReturnsNull();
    }

    private static void testReturnsPreviousValueAndRemoves() {
        System.setProperty(KEY, "value1");
        String previous = System.clearProperty(KEY);
        check("value1".equals(previous), "expected previous value 'value1' but was " + previous);
        check(System.getProperty(KEY) == null, "property should be removed after clearProperty");
    }

    private static void testAbsentKeyReturnsNull() {
        System.clearProperty(KEY);
        String result = System.clearProperty(KEY);
        check(result == null, "clearProperty on absent key should return null");
    }

    private static void testEmptyValueIsReturned() {
        System.setProperty(KEY, "");
        String previous = System.clearProperty(KEY);
        check("".equals(previous), "expected empty previous value but was " + previous);
        check(System.getProperty(KEY) == null, "property should be removed after clearing empty value");
    }

    private static void testRepeatedClearReturnsNull() {
        System.setProperty(KEY, "v2");
        check("v2".equals(System.getProperty(KEY)), "precondition: property should be set");
        check("v2".equals(System.clearProperty(KEY)), "first clear should return 'v2'");
        check(System.clearProperty(KEY) == null, "second clear should return null");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
