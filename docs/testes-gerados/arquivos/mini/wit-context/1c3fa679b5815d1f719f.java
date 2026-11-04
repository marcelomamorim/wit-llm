/*
 * @test
 * @summary Test java.io.File.deleteOnExit() for expected behavior and exception paths
 * @run main FileWitupTest
 */

import java.io.File;
import java.security.Permission;

public class FileWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteOnExitNormal();
        testDeleteOnExitWithSecurityManagerRejectingDelete();
        testDeleteOnExitOnInvalidFile();
    }

    private static void testDeleteOnExitNormal() {
        File f = new File("testDeleteOnExitNormal.tmp");
        // Should not throw and should register file for deletion on exit
        f.deleteOnExit();
    }

    private static void testDeleteOnExitWithSecurityManagerRejectingDelete() {
        final File f = new File("testDeleteOnExitWithSecurityManagerRejectingDelete.tmp");
        SecurityManager original = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkDelete(String file) {
                if (file.equals(f.getPath())) {
                    throw new SecurityException("Delete not allowed");
                }
            }
            @Override
            public void checkPermission(Permission perm) {
                // allow everything else
            }
        });
        boolean threw = false;
        try {
            f.deleteOnExit();
        } catch (SecurityException se) {
            threw = true;
        } finally {
            System.setSecurityManager(original);
        }
        if (!threw) {
            throw new RuntimeException("Expected SecurityException was not thrown");
        }
    }

    private static void testDeleteOnExitOnInvalidFile() {
        // Create a subclass to override isInvalid() to return true
        File invalidFile = new File("invalid") {
            @Override
            boolean isInvalid() {
                return true;
            }
        };
        // Calling deleteOnExit on invalid file should return silently without exception
        invalidFile.deleteOnExit();
    }
}
