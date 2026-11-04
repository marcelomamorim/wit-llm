/*
 * @test
 * @summary Witup-generated tests for System.setProperty exception paths and return values
 * @run main SystemWitupTest
 */

public class SystemWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void expectNPE(String key, String value, String label) {
        try {
            System.setProperty(key, value);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException: " + label);
    }

    private static void expectIAE(String key, String value, String label) {
        try {
            System.setProperty(key, value);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException: " + label);
    }

    public static void main(String[] args) {
        String key = "witup.system.setProperty.test";
        System.clearProperty(key);

        check(System.setProperty(key, "v1") == null, "first set should return null");
        check("v1".equals(System.getProperty(key)), "value should be v1 after first set");

        check("v1".equals(System.setProperty(key, "v2")), "second set should return previous value v1");
        check("v2".equals(System.getProperty(key)), "value should be v2 after second set");

        expectNPE(null, "x", "null key");
        expectIAE("", "x", "empty key");

        expectNPE(key, null, "null value");
        check("v2".equals(System.getProperty(key)), "failed null-value set must not change existing value");

        System.clearProperty(key);
        check(System.getProperty(key) == null, "property should be absent after clearProperty");
    }
}
