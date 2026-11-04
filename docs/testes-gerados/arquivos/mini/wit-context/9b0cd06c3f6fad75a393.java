/*
 * @test
 * @summary Test FileInputStream(FileDescriptor) constructor for null argument and security check
 * @run main FileInputStreamWitupTest
 */

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.lang.SecurityManager;

public class FileInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullFileDescriptorThrowsNPE();
        testValidFileDescriptor();
    }

    private static void testNullFileDescriptorThrowsNPE() {
        try {
            new FileInputStream((FileDescriptor) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testValidFileDescriptor() throws Exception {
        // Create a dummy FileDescriptor (FileDescriptor.in is public static final)
        FileDescriptor fd = FileDescriptor.in;

        // Install a SecurityManager that denies checkRead to test security check invocation
        SecurityManager original = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkRead(FileDescriptor fdObj) {
                if (fdObj == fd) {
                    // Allow
                    return;
                }
                throw new SecurityException("checkRead denied");
            }

            @Override
            public void checkPermission(java.security.Permission perm) {
                // Allow all other permissions
            }
        });

        try {
            FileInputStream fis = new FileInputStream(fd);
            if (fis == null) {
                throw new RuntimeException("FileInputStream constructor returned null");
            }
            fis.close();
        } finally {
            System.setSecurityManager(original);
        }
    }
}
