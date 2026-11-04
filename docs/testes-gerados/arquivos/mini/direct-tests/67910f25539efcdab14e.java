/*
 * @test
 * @summary Test java.io.File.canExecute() method behavior
 * @run main FileWitupTest
 */

import java.io.File;
import java.io.FileSystem;
import java.security.Permission;

public class FileWitupTest {

    public static void main(String[] args) throws Exception {
        testCanExecuteWithoutSecurityManager();
        testCanExecuteWithSecurityManagerAllowingExec();
        testCanExecuteWithSecurityManagerDenyingExec();
    }

    private static void testCanExecuteWithoutSecurityManager() {
        System.setSecurityManager(null);
        File f = new File(".");
        // The file '.' should be valid and accessible, so canExecute should return true or false but not throw
        boolean result = f.canExecute();
        if (result != true && result != false) {
            throw new AssertionError("canExecute returned invalid boolean");
        }
    }

    private static void testCanExecuteWithSecurityManagerAllowingExec() {
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkExec(String cmd) {
                // allow execution
            }
            @Override
            public void checkPermission(Permission perm) {
                // allow all other permissions
            }
        });
        File f = new File(".");
        boolean result = f.canExecute();
        if (result != true && result != false) {
            throw new AssertionError("canExecute returned invalid boolean with security manager allowing exec");
        }
    }

    private static void testCanExecuteWithSecurityManagerDenyingExec() {
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkExec(String cmd) {
                throw new SecurityException("Execution not allowed");
            }
            @Override
            public void checkPermission(Permission perm) {
                // allow all other permissions
            }
        });
        File f = new File(".");
        try {
            f.canExecute();
            throw new AssertionError("Expected SecurityException not thrown");
        } catch (SecurityException e) {
            // expected
        }
    }
}
