/*
 * @test
 * @summary Test FileOutputStream(FileDescriptor) constructor for null argument and normal usage
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
            new FileOutputStream((FileDescriptor) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testValidFileDescriptor() throws Exception {
        // Use the standard out FileDescriptor which is always valid
        FileDescriptor fd = FileDescriptor.out;

        // SecurityManager checkWrite may throw SecurityException if security manager denies
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            try {
                sm.checkWrite(fd);
            } catch (SecurityException se) {
                // Security manager denies write, skip test
                return;
            }
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fd);
            if (fos == null) {
                throw new RuntimeException("FileOutputStream constructor returned null");
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}
