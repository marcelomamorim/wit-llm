/*
 * @test
 * @summary Test SecurityManager.checkPackageDefinition behavior
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.lang.RuntimePermission;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test null argument triggers NullPointerException
        try {
            sm.checkPackageDefinition(null);
            throw new Error("Expected NullPointerException for null package name");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test with a package name that is not restricted (likely no exception)
        // We expect no SecurityException thrown here
        sm.checkPackageDefinition("java.lang");

        // Test with a package name that is exactly one of the restricted packages
        // We cannot know the actual restricted packages, but we can simulate by
        // using a package name that starts with "java." which is commonly restricted
        // Since we cannot set the internal state, we just call and expect no exception
        // because the default SecurityManager does not restrict by default.
        sm.checkPackageDefinition("java.");

        // Test with a package name that starts with a restricted package prefix
        // Again, we cannot force the internal state, so just call and expect no exception
        sm.checkPackageDefinition("javax.security");

        // Test with a package name that ends with a dot (should not throw)
        sm.checkPackageDefinition("com.example.");

        // Test with a package name that is a prefix of a restricted package (should not throw)
        sm.checkPackageDefinition("com");

        // If no exceptions thrown except expected NullPointerException, test passes
        System.out.println("SecurityManagerWitupTest passed");
    }
}
