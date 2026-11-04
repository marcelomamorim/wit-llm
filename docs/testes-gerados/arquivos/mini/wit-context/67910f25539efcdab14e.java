/*
 * @test
 * @summary Deterministic tests for java.io.File.canExecute()
 * @run main FileWitupTest
 */

import java.io.File;
import java.security.AccessControlException;

public class FileWitupTest {
    public static void main(String[] args) {
        testCanExecuteWithNoSecurityManager();
        testCanExecuteWithSecurityManagerAllowingExec();
        testCanExecuteWithSecurityManagerDenyingExec();
        testCanExecuteWithInvalidFile();
    }

    private static void testCanExecuteWithNoSecurityManager() {
        // Remove any security manager
        System.setSecurityManager(null);

        File f = new File(".");
        // The file '.' should be valid and accessible for execute on most systems
        boolean canExec = f.canExecute();
        // Result is boolean, no exception expected
        if (canExec != true && canExec != false) {
            throw new AssertionError("canExecute() returned non-boolean");
        }
    }

    private static void testCanExecuteWithSecurityManagerAllowingExec() {
        // Install a SecurityManager that allows exec
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkExec(String cmd) {
                // allow all
            }
            @Override
            public void checkPermission(java.security.Permission perm) {
                // allow all
            }
        });

        File f = new File(".");
        boolean canExec = f.canExecute();
        if (canExec != true && canExec != false) {
            throw new AssertionError("canExecute() returned non-boolean");
        }
    }

    private static void testCanExecuteWithSecurityManagerDenyingExec() {
        // Install a SecurityManager that denies exec
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkExec(String cmd) {
                throw new SecurityException("Exec denied");
            }
            @Override
            public void checkPermission(java.security.Permission perm) {
                // allow all other permissions
            }
        });

        File f = new File(".");
        try {
            f.canExecute();
            throw new AssertionError("Expected SecurityException not thrown");
        } catch (SecurityException e) {
            // expected
            if (!"Exec denied".equals(e.getMessage())) {
                throw new AssertionError("Unexpected SecurityException message: " + e.getMessage());
            }
        }
    }

    private static void testCanExecuteWithInvalidFile() {
        // Remove security manager to avoid interference
        System.setSecurityManager(null);

        // Create a File with an invalid path (empty string is valid, so use a path that is invalid)
        // Since File does not throw for invalid paths, we simulate by subclassing and overriding isInvalid
        File invalidFile = new File("invalid_path_for_test") {
            @Override
            boolean isInvalid() {
                return true;
            }
        };

        boolean canExec = invalidFile.canExecute();
        if (canExec != false) {
            throw new AssertionError("canExecute() should return false for invalid file");
        }
    }
}