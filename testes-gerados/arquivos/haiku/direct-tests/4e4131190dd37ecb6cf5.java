/*
 * @test
 * @summary Tests SecurityManager.checkLink null handling and permission delegation
 * @run main SecurityManagerWitupTest
 */

import java.security.Permission;

public class SecurityManagerWitupTest {

    static class RecordingSM extends SecurityManager {
        Permission last;
        int calls;

        @Override
        public void checkPermission(Permission perm) {
            calls++;
            last = perm;
        }
    }

    static class DenyingSM extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
            throw new SecurityException("denied");
        }
    }

    public static void main(String[] args) {
        RecordingSM sm = new RecordingSM();

        sm.checkLink("foo");
        if (sm.calls != 1) {
            throw new RuntimeException("expected 1 permission check, got " + sm.calls);
        }
        if (!(sm.last instanceof RuntimePermission)) {
            throw new RuntimeException("expected RuntimePermission, got " + sm.last);
        }
        if (!"loadLibrary.foo".equals(sm.last.getName())) {
            throw new RuntimeException("unexpected permission name: " + sm.last.getName());
        }

        try {
            sm.checkLink(null);
            throw new RuntimeException("expected NullPointerException for null library");
        } catch (NullPointerException e) {
            // expected
        }
        if (sm.calls != 1) {
            throw new RuntimeException("checkPermission must not be called for null library, calls=" + sm.calls);
        }

        DenyingSM denying = new DenyingSM();
        try {
            denying.checkLink("bar");
            throw new RuntimeException("expected SecurityException from checkPermission");
        } catch (SecurityException e) {
            // expected
        }
    }
}
