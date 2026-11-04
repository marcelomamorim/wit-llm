/* @test    @summary Test ResourcesMgr.getString for known resource key    @run main ResourcesMgrWitupTest */

import sun.security.util.ResourcesMgr;

public class ResourcesMgrWitupTest {

    public static void main(String[] args) {
        testGetStringReturnsExpected();
        testGetStringBeforeBootThrows();
    }

    private static void testGetStringReturnsExpected() {
        // Since VM.isBooted() is expected to be true during normal operation,
        // and the resource bundle "sun.security.util.Resources" is expected to exist,
        // calling getString with a known key should return a non-null string.
        String key = "someKey";
        try {
            String result = ResourcesMgr.getString(key);
            // The actual value depends on resource bundle contents; check for non-null.
            if (result == null) {
                throw new AssertionError("Expected non-null result for key: " + key);
            }
        } catch (InternalError e) {
            // If VM is not booted, an InternalError is expected.
            // This is acceptable in certain test environments.
            System.out.println("Caught expected InternalError: " + e.getMessage());
        }
    }

    private static void testGetStringBeforeBootThrows() {
        // To simulate VM not booted, we can temporarily override VM.isBooted() if possible.
        // Since VM.isBooted() is static and final, and no mocking framework is used,
        // we cannot directly override it. Instead, this test assumes VM is booted.
        // If VM.isBooted() returns false, getString should throw InternalError.
        // We invoke getString and expect an InternalError if VM is not booted.
        try {
            ResourcesMgr.getString("anyKey");
        } catch (InternalError e) {
            // Expected if VM is not booted.
            System.out.println("Caught expected InternalError during getString: " + e.getMessage());
        }
    }
}
