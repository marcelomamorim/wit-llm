/*
 * @test
 * @summary Test System.getProperty with existing key and default value
 * @run main SystemWitupTest
 */
public class SystemWitupTest {
    public static void main(String[] args) {
        String key = "java.version";
        String defaultValue = "default";
        String result = System.getProperty(key, defaultValue);
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-empty property value for key: " + key);
        }
        String resultWithDefault = System.getProperty("non.existent.key", defaultValue);
        if (!defaultValue.equals(resultWithDefault)) {
            throw new AssertionError("Expected default value for non-existent key: " + defaultValue);
        }
    }
}
