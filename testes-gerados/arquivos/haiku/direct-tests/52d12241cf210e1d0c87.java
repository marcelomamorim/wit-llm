/*
 * @test
 * @summary SecurityManager.checkWrite(FileDescriptor) rejects null and requests writeFileDescriptor permission
 * @run main SecurityManagerWitupTest
 */

import java.io.FileDescriptor;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class SecurityManagerWitupTest {

    static class RecordingManager extends SecurityManager {
        final List<Permission> perms = new ArrayList<>();
        boolean deny;

        @Override
        public void checkPermission(Permission perm) {
            perms.add(perm);
            if (deny) {
                throw new SecurityException("denied");
            }
        }
    }

    public static void main(String[] args) {
        testNullFileDescriptor();
        testRequestsWriteFileDescriptorPermission();
        testPermissionDenialPropagates();
    }

    static void testNullFileDescriptor() {
        RecordingManager m = new RecordingManager();
        try {
            m.checkWrite(null);
            throw new RuntimeException("NullPointerException expected");
        } catch (NullPointerException e) {
            if (!"file descriptor can't be null".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!m.perms.isEmpty()) {
            throw new RuntimeException("checkPermission must not be called for null fd");
        }
    }

    static void testRequestsWriteFileDescriptorPermission() {
        RecordingManager m = new RecordingManager();
        m.checkWrite(new FileDescriptor());
        if (m.perms.size() != 1) {
            throw new RuntimeException("expected one permission check, got " + m.perms.size());
        }
        Permission p = m.perms.get(0);
        if (!(p instanceof RuntimePermission)) {
            throw new RuntimeException("expected RuntimePermission, got " + p.getClass());
        }
        if (!"writeFileDescriptor".equals(p.getName())) {
            throw new RuntimeException("unexpected permission name: " + p.getName());
        }
    }

    static void testPermissionDenialPropagates() {
        RecordingManager m = new RecordingManager();
        m.deny = true;
        try {
            m.checkWrite(new FileDescriptor());
            throw new RuntimeException("SecurityException expected");
        } catch (SecurityException e) {
            if (!"denied".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }
}
