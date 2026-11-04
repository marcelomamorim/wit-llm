/*
 * @test
 * @summary Verifies SecurityManager.checkConnect builds the expected SocketPermission for connect and resolve checks
 * @run main SecurityManagerWitupTest
 */

import java.net.SocketPermission;
import java.security.Permission;

public class SecurityManagerWitupTest {

    static class RecordingManager extends SecurityManager {
        Permission last;
        int count;

        @Override
        public void checkPermission(Permission perm) {
            last = perm;
            count++;
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        RecordingManager sm = new RecordingManager();

        boolean npe = false;
        try {
            sm.checkConnect(null, 80);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null host must throw NullPointerException");
        check(sm.count == 0, "no permission check expected for null host");

        sm.checkConnect("127.0.0.1", 80);
        check(sm.count == 1, "exactly one checkPermission call expected");
        check(sm.last instanceof SocketPermission, "permission must be SocketPermission");
        check("connect".equals(sm.last.getActions()), "connect action expected");
        check("127.0.0.1:80".equals(sm.last.getName()), "host:port name expected");

        sm.checkConnect("127.0.0.1", -1);
        check(sm.count == 2, "second checkPermission call expected");
        check(sm.last instanceof SocketPermission, "resolve permission must be SocketPermission");
        check("resolve".equals(sm.last.getActions()), "resolve action expected for port -1");

        sm.checkConnect("::1", 8080);
        String name = sm.last.getName();
        check(name.startsWith("["), "IPv6 host with colon must be bracketed");
        check(name.indexOf("[[") < 0, "IPv6 host must not be double bracketed");
        check(name.endsWith(":8080"), "port must be appended");
        check("connect".equals(sm.last.getActions()), "connect action expected for IPv6");

        sm.checkConnect("[::1]", 8080);
        name = sm.last.getName();
        check(name.startsWith("["), "already bracketed IPv6 host must stay bracketed");
        check(name.indexOf("[[") < 0, "already bracketed host must not be re-bracketed");
        check(name.endsWith(":8080"), "port must be appended for bracketed host");
    }
}
