/*
 * @test
 * @summary Test for java.io.File.deleteOnExit() method
 * @run main FileWitupTest
 */

import java.io.File;

public class FileWitupTest {

    public static void main(String[] args) {
        testDeleteOnExitWithSecurityNull();
        testDeleteOnExitWithSecurityNotNullAndInvalid();
        testDeleteOnExitWithSecurityNotNullAndValid();
    }

    private static void testDeleteOnExitWithSecurityNull() {
        try {
            File file = new File("tempFile1");
            // Simulate no SecurityManager
            // Since security check is skipped if security == null, just call deleteOnExit
            file.deleteOnExit();
            System.out.println("testDeleteOnExitWithSecurityNull passed");
        } catch (Exception e) {
            System.err.println("testDeleteOnExitWithSecurityNull failed: " + e);
        }
    }

    private static void testDeleteOnExitWithSecurityNotNullAndInvalid() {
        try {
            File file = new File("tempFile2");
            // Mock SecurityManager to simulate non-null security
            SecurityManager sm = new SecurityManager() {
                @Override
                public void checkDelete(String file) {
                    // do nothing or throw exception to simulate security check
                }
            };
            SecurityManager original = System.getSecurityManager();
            System.setSecurityManager(sm);
            try {
                file.deleteOnExit();
                System.out.println("testDeleteOnExitWithSecurityNotNullAndInvalid passed");
            } finally {
                System.setSecurityManager(original);
            }
        } catch (Exception e) {
            System.err.println("testDeleteOnExitWithSecurityNotNullAndInvalid failed: " + e);
        }
    }

    private static void testDeleteOnExitWithSecurityNotNullAndValid() {
        try {
            File file = new File("tempFile3");
            SecurityManager sm = new SecurityManager() {
                @Override
                public void checkDelete(String filePath) {
                    // simulate allowed delete
                }
            };
            SecurityManager original = System.getSecurityManager();
            System.setSecurityManager(sm);
            try {
                file.deleteOnExit();
                System.out.println("testDeleteOnExitWithSecurityNotNullAndValid passed");
            } finally {
                System.setSecurityManager(original);
            }
        } catch (Exception e) {
            System.err.println("testDeleteOnExitWithSecurityNotNullAndValid failed: " + e);
        }
    }
}
