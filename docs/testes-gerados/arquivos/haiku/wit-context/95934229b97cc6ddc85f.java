/*
 * @test
 * @summary Verify SecurityManager.checkConnect null-host NPE and permission construction observable behavior
 * @run main SecurityManagerWitupTest
 */

import java.net.SocketPermission;
import java.security.Permission;

public class SecurityManagerWitupTest {

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static class RecordingSecurityManager extends SecurityManager {
        int count;
        Permission last;

        @Override
        public void checkPermission(Permission perm) {
            count++;
            last = perm;
        }
    }

    static void testNullHostThrowsNPE() {
        try {
            new SecurityManager().checkConnect(null, 80);
            fail("Expected NullPointerException for null host");
        } catch (NullPointerException e) {
            if (!"host can't be null".equals(e.getMessage())) {
                fail("Unexpected NPE message: " + e.getMessage());
            }
        }
    }

    static void testLiteralHostConnectPermission() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        sm.checkConnect("127.0.0.1", 80);
        if (sm.count != 1) {
            fail("Expected exactly one checkPermission call, got " + sm.count);
        }
        if (!(sm.last instanceof SocketPermission)) {
            fail("Expected SocketPermission, got " + sm.last);
        }
        if (!sm.last.getName().startsWith("127.0.0.1:80")) {
            fail("Unexpected permission name: " + sm.last.getName());
        }
        if (!sm.last.getActions().contains("connect")) {
            fail("Expected connect action, got " + sm.last.getActions());
        }
    }

    static void testPortMinusOneResolvePermission() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        sm.checkConnect("127.0.0.1", -1);
        if (sm.count != 1) {
            fail("Expected exactly one checkPermission call, got " + sm.count);
        }
        if (!sm.last.getActions().contains("resolve")) {
            fail("Expected resolve action, got " + sm.last.getActions());
        }
    }

    static void testIPv6LiteralIsBracketed() {
        RecordingSecurityManager sm = new RecordingSecurityManager();
        sm.checkConnect("::1", 80);
        if (sm.count != 1) {
            fail("Expected exactly one checkPermission call, got " + sm.count);
        }
        if (!sm.last.getName().contains("::1")) {
            fail("IPv6 host missing from permission name: " + sm.last.getName());
        }
    }

    public static void main(String[] args) {
        testNullHostThrowsNPE();
        testLiteralHostConnectPermission();
        testPortMinusOneResolvePermission();
        testIPv6LiteralIsBracketed();
    }
}
