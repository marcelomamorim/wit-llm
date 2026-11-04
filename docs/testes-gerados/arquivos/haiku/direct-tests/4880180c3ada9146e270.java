/*
 * @test
 * @summary SecurityManager.checkExec builds a FilePermission with execute action, using the absolute path for absolute files and <<ALL FILES>> otherwise
 * @run main SecurityManagerWitupTest
 */

import java.io.File;
import java.io.FilePermission;
import java.security.Permission;

public class SecurityManagerWitupTest {

    static class RecordingSecurityManager extends SecurityManager {
        Permission last;
        int calls;
        boolean throwOnCheck;

        @Override
        public void checkPermission(Permission perm) {
            calls++;
            last = perm;
            if (throwOnCheck) {
                throw new SecurityException("denied");
            }
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        RecordingSecurityManager sm = new RecordingSecurityManager();

        String absolute = new File("witup-exec-target").getAbsolutePath();
        sm.checkExec(absolute);
        check(sm.calls == 1, "checkPermission should be called once for absolute path");
        check(sm.last instanceof FilePermission, "expected FilePermission");
        check("execute".equals(sm.last.getActions()), "action should be execute");
        check(!"<<ALL FILES>>".equals(sm.last.getName()), "absolute path must not map to <<ALL FILES>>");

        sm.checkExec("witup-relative-cmd");
        check(sm.calls == 2, "checkPermission should be called for relative path");
        check(sm.last instanceof FilePermission, "expected FilePermission for relative path");
        check("<<ALL FILES>>".equals(sm.last.getName()), "relative command should map to <<ALL FILES>>");
        check("execute".equals(sm.last.getActions()), "relative action should be execute");

        sm.throwOnCheck = true;
        boolean thrown = false;
        try {
            sm.checkExec("witup-relative-cmd");
        } catch (SecurityException e) {
            thrown = true;
        }
        check(thrown, "checkExec must propagate SecurityException from checkPermission");
    }
}
