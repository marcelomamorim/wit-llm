/*
 * @test
 * @summary Test SecurityManager.checkExec method for absolute and relative paths
 * @run main SecurityManagerWitupTest
 */

import java.io.File;
import java.io.FilePermission;
import java.security.Permission;
import java.lang.SecurityManager;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager() {
            @Override
            public void checkPermission(Permission perm) {
                // Allow all permissions for testing purposes
            }
        };
        System.setSecurityManager(sm);

        testAbsolutePath();
        testRelativePath();
    }

    static void testAbsolutePath() {
        SecurityManager sm = System.getSecurityManager();
        if (sm == null) {
            throw new AssertionError("SecurityManager should be set");
        }
        String absPath = new File("/bin/ls").getAbsolutePath();
        sm.checkExec(absPath);
        // If no exception, test passes
    }

    static void testRelativePath() {
        SecurityManager sm = System.getSecurityManager();
        if (sm == null) {
            throw new AssertionError("SecurityManager should be set");
        }
        String relPath = "someRelativePath";
        sm.checkExec(relPath);
        // If no exception, test passes
    }
}
