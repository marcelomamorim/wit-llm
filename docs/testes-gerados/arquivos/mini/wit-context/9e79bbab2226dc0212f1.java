/*
 * @test
 * @summary Test requireServiceProviderName method in jdk.internal.module.Checks
 * @run main ChecksWitupTest
 */

public class ChecksWitupTest {
    public static void main(String[] args) {
        testRequireServiceProviderNameNull();
        testRequireServiceProviderNameValid();
    }

    private static void testRequireServiceProviderNameNull() {
        try {
            jdk.internal.module.Checks.requireServiceProviderName(null);
            throw new Error("Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("service provider name")) {
                throw new Error("Exception message does not mention 'service provider name': " + msg);
            }
        }
    }

    private static void testRequireServiceProviderNameValid() {
        // Valid qualified class name examples
        String[] validNames = {
            "java.lang.String",
            "com.example.MyServiceProvider",
            "A",
            "a.b.C"
        };
        for (String name : validNames) {
            String result = jdk.internal.module.Checks.requireServiceProviderName(name);
            if (!result.equals(name)) {
                throw new Error("requireServiceProviderName did not return the input name: " + name);
            }
        }
    }
}
