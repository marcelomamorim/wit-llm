/*
 * @test
 * @summary Verify SecurityManager.checkRead(FileDescriptor) null handling and permission check delegation
 * @run main SecurityManagerWitupTest
 */

import java.io.FileDescriptor;
import java.security.Permission;

public class SecurityManagerWitupTest {

    static class RecordingSecurityManager extends SecurityManager {
        int calls;
        Permission last;

        @Override
        public void checkPermission(Permission perm) {
            calls++;
            last = perm;
        }
    }

    static class DenyingSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
            throw new SecurityException("denied");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFileDescriptorThrowsNpe() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        try {
            sm.checkRead(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            check("file descriptor can't be null".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(sm.calls == 0, "permission check must not run for null fd");
    }

    static void testValidFileDescriptorChecksReadPermission() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        sm.checkRead(FileDescriptor.in);
        check(sm.calls == 1, "expected exactly one permission check, got " + sm.calls);
        check(sm.last instanceof RuntimePermission, "expected RuntimePermission");
        check("readFileDescriptor".equals(sm.last.getName()),
              "unexpected permission name: " + sm.last.getName());
    }

    static void testDeniedPermissionPropagates() {
        DenyingSecurityManager sm = new DenyingSecurityManager();
        try {
            sm.checkRead(FileDescriptor.in);
            throw new RuntimeException("expected SecurityException");
        } catch (SecurityException e) {
            check("denied".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void testNullCheckPrecedesPermissionCheck() {
        DenyingSecurityManager sm = new DenyingSecurityManager();
        try {
            sm.checkRead(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            check("file descriptor can't be null".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        testNullFileDescriptorThrowsNpe();
        testValidFileDescriptorChecksReadPermission();
        testDeniedPermissionPropagates();
        testNullCheckPrecedesPermissionCheck();
    }
}
