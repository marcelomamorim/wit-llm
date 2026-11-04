/*
 * @test
 * @summary Test SecurityManager.checkExec(String) with absolute and relative paths
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.io.FilePermission;
import java.security.Permission;
import java.security.SecurityException;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager() {
            // Override checkPermission to observe the permission checked
            @Override
            public void checkPermission(Permission perm) {
                if (!(perm instanceof FilePermission)) {
                    throw new SecurityException("Unexpected permission type");
                }
                FilePermission fp = (FilePermission) perm;
                String actions = fp.getActions();
                String name = fp.getName();

                // Check that the action is always "execute"
                if (!"execute".equals(actions)) {
                    throw new SecurityException("Expected execute action, got: " + actions);
                }

                // For absolute path, name should be the exact cmd
                // For relative path, name should be "<<ALL FILES>>"
                if (name.equals("<<ALL FILES>>")) {
                    // relative path case
                    if (name.isEmpty()) {
                        throw new SecurityException("Name should not be empty");
                    }
                } else {
                    // absolute path case
                    if (!name.equals(expectedAbsolutePath)) {
                        throw new SecurityException("Expected absolute path '" + expectedAbsolutePath + "' but got '" + name + "'");
                    }
                }
            }

            String expectedAbsolutePath;
        };

        // Test with absolute path
        String absPath = "/bin/ls";
        ((SecurityManagerWitupTest.SecurityManagerWithExpectedPath)sm).expectedAbsolutePath = absPath;
        try {
            sm.checkExec(absPath);
        } catch (SecurityException se) {
            throw new RuntimeException("checkExec failed for absolute path", se);
        }

        // Test with relative path
        try {
            sm.checkExec("relative/path/to/executable");
        } catch (SecurityException se) {
            throw new RuntimeException("checkExec failed for relative path", se);
        }

        // Test with empty string (relative path)
        try {
            sm.checkExec("");
        } catch (SecurityException se) {
            throw new RuntimeException("checkExec failed for empty string path", se);
        }
    }

    // Helper subclass to hold expected absolute path
    static class SecurityManagerWithExpectedPath extends SecurityManager {
        String expectedAbsolutePath;

        @Override
        public void checkPermission(Permission perm) {
            if (!(perm instanceof FilePermission)) {
                throw new SecurityException("Unexpected permission type");
            }
            FilePermission fp = (FilePermission) perm;
            String actions = fp.getActions();
            String name = fp.getName();

            if (!"execute".equals(actions)) {
                throw new SecurityException("Expected execute action, got: " + actions);
            }

            if (name.equals("<<ALL FILES>>")) {
                // relative path case
                if (name.isEmpty()) {
                    throw new SecurityException("Name should not be empty");
                }
            } else {
                // absolute path case
                if (!name.equals(expectedAbsolutePath)) {
                    throw new SecurityException("Expected absolute path '" + expectedAbsolutePath + "' but got '" + name + "'");
                }
            }
        }
    }
}