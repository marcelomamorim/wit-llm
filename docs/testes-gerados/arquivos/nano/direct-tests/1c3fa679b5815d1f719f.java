/* @test    @summary Test File.deleteOnExit() method for normal behavior and security check invocation    @run main FileWitupTest */

import java.io.File;
import java.security.Security;

public class FileWitupTest {

    public static void main(String[] args) {
        testDeleteOnExitWithValidPath();
        testDeleteOnExitWithNullPath();
        testDeleteOnExitWithSecurityManager();
    }

    private static void testDeleteOnExitWithValidPath() {
        String tempPath = System.getProperty("java.io.tmpdir") + "/testFileDeleteOnExit";
        File file = new File(tempPath);
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (!created) {
                    System.out.println("Failed to create test file.");
                    return;
                }
            }
            file.deleteOnExit();
            System.out.println("deleteOnExit invoked successfully for existing file.");
        } catch (Exception e) {
            System.out.println("Exception during testDeleteOnExitWithValidPath: " + e);
        }
    }

    private static void testDeleteOnExitWithNullPath() {
        try {
            File file = new File((String) null);
            file.deleteOnExit();
            System.out.println("deleteOnExit invoked successfully for null path.");
        } catch (NullPointerException npe) {
            System.out.println("NullPointerException caught as expected for null path.");
        } catch (Exception e) {
            System.out.println("Unexpected exception for null path: " + e);
        }
    }

    private static void testDeleteOnExitWithSecurityManager() {
        SecurityManager originalSecurity = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkDelete(String file) {
                throw new SecurityException("Security check failed for delete.");
            }
        });
        try {
            File file = new File("dummyPath");
            try {
                file.deleteOnExit();
                System.out.println("deleteOnExit should have thrown SecurityException.");
            } catch (SecurityException se) {
                System.out.println("SecurityException caught as expected.");
            }
        } finally {
            System.setSecurityManager(originalSecurity);
        }
    }
}
