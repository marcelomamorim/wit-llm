/*
 * @test
 * @summary Test SecurityManager.checkWrite(FileDescriptor) for null argument and permission check
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.io.FileDescriptor;
import java.security.AccessControlException;
import java.security.Permission;
import java.security.RuntimePermission;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test that passing null FileDescriptor throws NullPointerException
        try {
            sm.checkWrite((FileDescriptor) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"file descriptor can't be null".equals(npe.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + npe.getMessage());
            }
        }

        // Test that checkWrite calls checkPermission with RuntimePermission("writeFileDescriptor")
        // Since default SecurityManager allows all, no exception expected
        // To test permission denial, install a custom SecurityManager

        SecurityManager testSM = new SecurityManager() {
            boolean permissionChecked = false;

            @Override
            public void checkPermission(Permission perm) {
                if (perm instanceof RuntimePermission && "writeFileDescriptor".equals(perm.getName())) {
                    permissionChecked = true;
                    // Deny permission to test exception throwing
                    throw new SecurityException("Permission denied: " + perm);
                }
                // Allow other permissions
            }

            @Override
            public void checkWrite(FileDescriptor fd) {
                super.checkWrite(fd);
            }
        };

        // Set the custom SecurityManager
        System.setSecurityManager(testSM);

        try {
            testSM.checkWrite(new FileDescriptor());
            throw new RuntimeException("Expected SecurityException not thrown");
        } catch (SecurityException se) {
            if (!se.getMessage().contains("writeFileDescriptor")) {
                throw new RuntimeException("Unexpected SecurityException message: " + se.getMessage());
            }
            if (!((SecurityManager) System.getSecurityManager()).permissionChecked) {
                throw new RuntimeException("Permission check was not performed");
            }
        } finally {
            // Restore original SecurityManager
            System.setSecurityManager(null);
        }
    }
}
