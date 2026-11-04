/*
 * @test
 * @summary Verify SecurityManager.checkAccept null handling, host bracket normalization, and permission propagation
 * @run main SecurityManagerWitupTest
 */

import java.net.SocketPermission;
import java.security.Permission;

public class SecurityManagerWitupTest {

    static class RecordingSecurityManager extends SecurityManager {
        Permission last;
        int calls;
        RuntimeException toThrow;

        @Override
        public void checkPermission(Permission perm) {
            calls++;
            last = perm;
            if (toThrow != null) {
                throw toThrow;
            }
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullHostThrowsNPE() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        boolean thrown = false;
        try {
            sm.checkAccept(null, 80);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null host must throw NullPointerException");
        check(sm.calls == 0, "checkPermission must not be called for null host");
    }

    static void testIPv4PassesAcceptPermission() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        sm.checkAccept("127.0.0.1", 8080);
        check(sm.calls == 1, "checkPermission must be called once");
        check(sm.last instanceof SocketPermission, "permission must be SocketPermission");
        check("accept".equals(sm.last.getActions()), "actions must be accept, got " + sm.last.getActions());
        check("127.0.0.1:8080".equals(sm.last.getName()),
              "name must be 127.0.0.1:8080, got " + sm.last.getName());
    }

    static void testIPv6GetsBracketed() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        sm.checkAccept("::1", 80);
        check(sm.calls == 1, "checkPermission must be called once for IPv6");
        String name = sm.last.getName();
        check(name.startsWith("["), "IPv6 host must be bracketed, got " + name);
        check(name.endsWith("]:80"), "IPv6 name must end with ]:80, got " + name);
        check(name.indexOf("[[") == -1, "host must not be double-bracketed, got " + name);
    }

    static void testAlreadyBracketedNotWrappedAgain() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        sm.checkAccept("[::1]", 443);
        String name = sm.last.getName();
        check(name.indexOf("[[") == -1, "already bracketed host must not be wrapped again, got " + name);
        check(name.endsWith("]:443"), "name must end with ]:443, got " + name);
    }

    static void testPermissionExceptionPropagates() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        sm.toThrow = new SecurityException("denied");
        boolean thrown = false;
        try {
            sm.checkAccept("127.0.0.1", 22);
        } catch (SecurityException e) {
            thrown = "denied".equals(e.getMessage());
        }
        check(thrown, "SecurityException from checkPermission must propagate");
    }

    public static void main(String[] args) {
        testNullHostThrowsNPE();
        testIPv4PassesAcceptPermission();
        testIPv6GetsBracketed();
        testAlreadyBracketedNotWrappedAgain();
        testPermissionExceptionPropagates();
    }
}
