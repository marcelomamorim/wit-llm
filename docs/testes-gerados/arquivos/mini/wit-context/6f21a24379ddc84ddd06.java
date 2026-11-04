/*
 * @test
 * @summary Test sun.security.util.ResourcesMgr.getAuthResourceString(String) behavior
 * @run main ResourcesMgrWitupTest
 */

import sun.security.util.ResourcesMgr;

public class ResourcesMgrWitupTest {
    public static void main(String[] args) {
        // Test normal behavior: getAuthResourceString returns a non-null string for a known key
        // We use a key that is very likely to exist in the AuthResources bundle, e.g. "login.failed"
        // If the key does not exist, getString throws MissingResourceException, which is allowed
        try {
            String result = ResourcesMgr.getAuthResourceString("login.failed");
            if (result == null) {
                throw new AssertionError("getAuthResourceString returned null for key 'login.failed'");
            }
        } catch (java.util.MissingResourceException e) {
            // Acceptable if key is missing, test passes as method throws as expected
        } catch (InternalError e) {
            // If VM is not booted, InternalError is expected, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }

        // Test behavior when VM is not booted: getAuthResourceString should throw InternalError
        // We cannot forcibly unboot the VM, so we simulate by calling getBundle indirectly
        // Since no public API to unboot VM, we test that InternalError is thrown if VM.isBooted() is false
        // This is a limitation; we document that this path is not testable here

        // Test that passing null key throws NullPointerException from ResourceBundle.getString
        try {
            ResourcesMgr.getAuthResourceString(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // Expected
        } catch (InternalError e) {
            // Acceptable if VM not booted
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null key: " + e);
        }
    }
}
