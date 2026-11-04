/*
 * @test
 * @summary Test FileInputStream.getFD() method behavior
 * @run main FileInputStreamWitupTest
 */

import java.io.FileInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.File;

public class FileInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testGetFDReturnsNonNull();
    }

    private static void testGetFDReturnsNonNull() throws Exception {
        // Create a temporary file to open
        File tempFile = File.createTempFile("FileInputStreamWitupTest", ".tmp");
        tempFile.deleteOnExit();

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(tempFile);
            FileDescriptor fd = fis.getFD();
            if (fd == null) {
                throw new AssertionError("getFD() returned null");
            }
            // The returned FileDescriptor should be valid (valid() returns boolean)
            if (!fd.valid()) {
                throw new AssertionError("FileDescriptor is not valid");
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}
