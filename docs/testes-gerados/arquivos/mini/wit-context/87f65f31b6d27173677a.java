/*
 * @test
 * @summary Test SecurityManager.checkRead(FileDescriptor) for null argument and permission check
 * @run main SecurityManagerWitupTest
 */

import java.io.FileDescriptor;
import java.security.AccessControlException;
import java.security.Permission;
import java.security.RuntimePermission;
import java.lang.SecurityManager;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test that passing null FileDescriptor throws NullPointerException
        try {
            sm.checkRead((FileDescriptor) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"file descriptor can't be null".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch: " + npe.getMessage());
            }
        }

        // Test that checkRead with a non-null FileDescriptor calls checkPermission
        // Since default SecurityManager.checkPermission calls AccessController.checkPermission,
        // and no SecurityManager is set, this should pass without exception.
        // We create a dummy FileDescriptor instance.
        FileDescriptor fd = new FileDescriptor();
        try {
            sm.checkRead(fd);
        } catch (SecurityException se) {
            throw new RuntimeException("Unexpected SecurityException thrown: " + se);
        }

        // Note: We cannot test the permission denial without installing a custom SecurityManager or policy,
        // which is outside the scope of this test and not safe to do in jtreg tests.
    }
}
