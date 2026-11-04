/*
 * @test
 * @summary Test FileOutputStream(FileDescriptor) constructor behavior
 * @run main FileOutputStreamWitupTest
 */

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.lang.SecurityManager;

public class FileOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullFileDescriptor();
        testValidFileDescriptor();
    }

    private static void testNullFileDescriptor() {
        try {
            new FileOutputStream(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testValidFileDescriptor() throws Exception {
        // Use the standard output FileDescriptor which is always valid
        FileDescriptor fd = FileDescriptor.out;

        // Install a SecurityManager that allows write to fd
        SecurityManager original = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkWrite(FileDescriptor fdObj) {
                if (fdObj == null) {
                    throw new SecurityException("FileDescriptor is null");
                }
                // allow write
            }

            @Override
            public void checkPermission(java.security.Permission perm) {
                // allow all other permissions
            }
        });

        try {
            FileOutputStream fos = new FileOutputStream(fd);
            if (fos == null) {
                throw new RuntimeException("FileOutputStream constructor returned null");
            }
            fos.close();
        } finally {
            System.setSecurityManager(original);
        }
    }
}
