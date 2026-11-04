/*
 * @test
 * @summary Test SecurityManager.checkWrite(FileDescriptor) behavior
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.io.FileDescriptor;
import java.security.Permission;
import java.security.RuntimePermission;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test null FileDescriptor throws NullPointerException
        try {
            sm.checkWrite(null);
            throw new Error("Expected NullPointerException for null FileDescriptor");
        } catch (NullPointerException npe) {
            if (!"file descriptor can't be null".equals(npe.getMessage())) {
                throw new Error("NullPointerException message mismatch");
            }
        }

        // Test with a valid FileDescriptor
        // Since default SecurityManager.checkPermission calls AccessController.checkPermission,
        // and no security manager is installed, this should pass silently.
        FileDescriptor fd = new FileDescriptor();
        sm.checkWrite(fd);

        // Test that checkPermission is called with RuntimePermission("writeFileDescriptor")
        // We do this by subclassing SecurityManager and overriding checkPermission
        final RuntimePermission expectedPerm = new RuntimePermission("writeFileDescriptor");
        SecurityManager smOverride = new SecurityManager() {
            boolean called = false;
            public void checkPermission(Permission perm) {
                if (perm instanceof RuntimePermission && "writeFileDescriptor".equals(perm.getName())) {
                    called = true;
                } else {
                    throw new SecurityException("Unexpected permission: " + perm);
                }
            }
            public void checkWrite(FileDescriptor fd) {
                super.checkWrite(fd);
            }
        };

        // Call checkWrite and verify checkPermission was called with expected permission
        try {
            smOverride.checkWrite(new FileDescriptor());
            if (!((Boolean)smOverride.getClass().getDeclaredField("called").get(smOverride))) {
                throw new Error("checkPermission was not called with expected RuntimePermission");
            }
        } catch (SecurityException se) {
            throw new Error("Unexpected SecurityException: " + se);
        } catch (ReflectiveOperationException roe) {
            throw new Error("Reflection failure: " + roe);
        }
    }
}
