/*
 * @test
 * @summary SecurityManager.checkRead(FileDescriptor) rejects null and checks readFileDescriptor permission
 * @run main SecurityManagerWitupTest
 */

import java.io.FileDescriptor;
import java.security.Permission;
import java.lang.RuntimePermission;

public class SecurityManagerWitupTest {

    static class RecordingSecurityManager extends SecurityManager {
        Permission last;
        int calls;

        @Override
        public void checkPermission(Permission perm) {
            last = perm;
            calls++;
        }
    }

    static class DenyingSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
            throw new SecurityException("denied");
        }
    }

    public static void main(String[] args) {
        testNullFileDescriptor();
        testPermissionChecked();
        testPermissionDenialPropagates();
    }

    static void testNullFileDescriptor() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        try {
            sm.checkRead(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            if (!"file descriptor can't be null".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (sm.calls != 0) {
            throw new RuntimeException("checkPermission called for null fd");
        }
    }

    static void testPermissionChecked() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        sm.checkRead(FileDescriptor.in);
        if (sm.calls != 1) {
            throw new RuntimeException("expected 1 permission check, got " + sm.calls);
        }
        if (!(sm.last instanceof RuntimePermission)) {
            throw new RuntimeException("expected RuntimePermission, got " + sm.last);
        }
        if (!"readFileDescriptor".equals(sm.last.getName())) {
            throw new RuntimeException("unexpected permission name: " + sm.last.getName());
        }
    }

    static void testPermissionDenialPropagates() {
        DenyingSecurityManager sm = new DenyingSecurityManager();
        try {
            sm.checkRead(FileDescriptor.in);
            throw new RuntimeException("expected SecurityException");
        } catch (SecurityException e) {
            if (!"denied".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }
}
