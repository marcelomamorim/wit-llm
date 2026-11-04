/*
 * @test
 * @summary Test java.io.File.deleteOnExit() behavior including security check and invalid path handling
 * @run main FileWitupTest
 */

import java.io.File;
import java.security.Permission;

public class FileWitupTest {

    static class TestSecurityManager extends SecurityManager {
        private String deletedPath = null;

        @Override
        public void checkDelete(String file) {
            deletedPath = file;
            // Allow deletion
        }

        @Override
        public void checkPermission(Permission perm) {
            // Allow all other permissions
        }

        String getDeletedPath() {
            return deletedPath;
        }
    }

    public static void main(String[] args) throws Exception {
        testDeleteOnExitWithoutSecurityManager();
        testDeleteOnExitWithSecurityManagerAllows();
        testDeleteOnExitWithInvalidPath();
    }

    private static void testDeleteOnExitWithoutSecurityManager() {
        // Remove any security manager
        System.setSecurityManager(null);

        File f = new File("testDeleteOnExitWithoutSecurityManager.tmp");
        // Should not throw and should register for delete on exit
        f.deleteOnExit();
    }

    private static void testDeleteOnExitWithSecurityManagerAllows() {
        TestSecurityManager sm = new TestSecurityManager();
        System.setSecurityManager(sm);

        String testPath = "testDeleteOnExitWithSecurityManagerAllows.tmp";
        File f = new File(testPath);

        f.deleteOnExit();

        if (!testPath.equals(sm.getDeletedPath())) {
            throw new RuntimeException("SecurityManager.checkDelete was not called with correct path");
        }

        // Reset security manager
        System.setSecurityManager(null);
    }

    private static void testDeleteOnExitWithInvalidPath() throws Exception {
        // Create a subclass of File to override isInvalid() to return true
        File f = new File("ignoredPath") {
            @Override
            boolean isInvalid() {
                return true;
            }
        };

        // Set a security manager that fails if checkDelete is called
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkDelete(String file) {
                throw new SecurityException("checkDelete should not be called for invalid path");
            }

            @Override
            public void checkPermission(java.security.Permission perm) {
                // allow all
            }
        });

        // Should return early and not throw
        f.deleteOnExit();

        // Reset security manager
        System.setSecurityManager(null);
    }
}
