/*
 * @test
 * @summary Test java.io.FileInputStream.getFD() method for normal and exceptional cases
 * @run main FileInputStreamWitupTest
 */

import java.io.FileInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.File;

public class FileInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testGetFDReturnsNonNull();
        testGetFDThrowsIOExceptionWhenFdNull();
    }

    private static void testGetFDReturnsNonNull() throws Exception {
        // Create a temporary file to ensure FileInputStream has a valid fd
        File tempFile = File.createTempFile("FileInputStreamWitupTest", ".tmp");
        tempFile.deleteOnExit();

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(tempFile);
            FileDescriptor fd = fis.getFD();
            if (fd == null) {
                throw new AssertionError("getFD() returned null for valid FileInputStream");
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    private static void testGetFDThrowsIOExceptionWhenFdNull() {
        // The source code shows getFD() throws IOException if fd == null
        // But fd is private final and always set in constructors, so no public way to create such instance
        // Hence, this test cannot be done via public API and is skipped.
        // This note is to document that the expath for IOException on fd==null is not testable here.
    }
}
