/*
 * @test
 * @summary Witup-generated checks for SecurityManager.checkExec(String)
 * @run main SecurityManagerWitupTest
 */

import java.io.File;
import java.io.FilePermission;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class SecurityManagerWitupTest {

    static class RecordingSecurityManager extends SecurityManager {
        final List<Permission> perms = new ArrayList<>();
        @Override
        public void checkPermission(Permission perm) {
            perms.add(perm);
        }
    }

    static class DenyingSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
            throw new SecurityException("denied");
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        RecordingSecurityManager sm = new RecordingSecurityManager();

        boolean npe = false;
        try {
            sm.checkExec(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "checkExec(null) must throw NullPointerException");

        sm.perms.clear();
        sm.checkExec("relative-tool");
        check(sm.perms.size() == 1, "relative path must trigger one permission check");
        Permission p1 = sm.perms.get(0);
        check(p1 instanceof FilePermission, "relative path must check a FilePermission");
        check("<<ALL FILES>>".equals(p1.getName()), "relative path must use <<ALL FILES>>");
        check("execute".equals(p1.getActions()), "permission action must be execute");

        sm.perms.clear();
        String abs = new File("tool").getAbsolutePath();
        check(new File(abs).isAbsolute(), "test setup: path must be absolute");
        sm.checkExec(abs);
        check(sm.perms.size() == 1, "absolute path must trigger one permission check");
        Permission p2 = sm.perms.get(0);
        check(p2 instanceof FilePermission, "absolute path must check a FilePermission");
        check(!"<<ALL FILES>>".equals(p2.getName()), "absolute path must not use <<ALL FILES>>");
        check("execute".equals(p2.getActions()), "permission action must be execute");

        boolean denied = false;
        try {
            new DenyingSecurityManager().checkExec("relative-tool");
        } catch (SecurityException e) {
            denied = true;
        }
        check(denied, "checkPermission failure must propagate from checkExec");
    }
}
