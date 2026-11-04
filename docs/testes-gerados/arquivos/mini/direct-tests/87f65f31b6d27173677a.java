/*
 * @test
 * @summary Test SecurityManager.checkRead(FileDescriptor) behavior
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.io.FileDescriptor;
import java.security.Permission;
import java.security.SecurityException;
import java.security.RuntimePermission;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();

        // Test that passing null throws NullPointerException
        try {
            sm.checkRead((FileDescriptor) null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            if (!"file descriptor can't be null".equals(npe.getMessage())) {
                throw new Error("NullPointerException message mismatch: " + npe.getMessage());
            }
        }

        // Test that checkRead calls checkPermission with RuntimePermission("readFileDescriptor")
        // Since default SecurityManager delegates to AccessController, and no security manager is set,
        // this should pass without exception.
        FileDescriptor fd = FileDescriptor.in; // standard input
        sm.checkRead(fd);

        // To test that checkPermission is called with correct permission, create a subclass overriding checkPermission
        final RuntimePermission expectedPerm = new RuntimePermission("readFileDescriptor");
        SecurityManager smOverride = new SecurityManager() {
            boolean called = false;
            public void checkPermission(Permission perm) {
                if (perm == null) {
                    throw new NullPointerException("Permission is null");
                }
                if (!expectedPerm.equals(perm)) {
                    throw new Error("Unexpected permission: " + perm);
                }
                called = true;
            }
            public void checkRead(FileDescriptor fd) {
                super.checkRead(fd);
                if (!called) {
                    throw new Error("checkPermission was not called");
                }
            }
        };

        smOverride.checkRead(fd);
    }
}