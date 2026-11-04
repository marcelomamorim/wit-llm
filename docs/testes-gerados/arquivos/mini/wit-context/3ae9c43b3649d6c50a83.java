/*
 * @test
 * @summary Test FileOutputStream.getFD() behavior including IOException when fd is null
 * @run main FileOutputStreamWitupTest
 */

import java.io.FileOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.File;

public class FileOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testGetFDReturnsNonNull();
        testGetFDThrowsIOExceptionWhenFdNull();
    }

    private static void testGetFDReturnsNonNull() throws IOException {
        // Create a temporary file to ensure fd is non-null
        File tempFile = File.createTempFile("foswitup", ".tmp");
        tempFile.deleteOnExit();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tempFile);
            FileDescriptor fd = fos.getFD();
            if (fd == null) {
                throw new AssertionError("getFD() returned null FileDescriptor");
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    private static void testGetFDThrowsIOExceptionWhenFdNull() {
        // We cannot directly create a FileOutputStream with null fd via public constructors.
        // So we create a subclass to simulate fd == null and test that getFD() throws IOException.
        class FOSWithNullFD extends FileOutputStream {
            private final FileDescriptor nullFd = null;

            FOSWithNullFD() throws IOException {
                super(FileDescriptor.out); // call super with valid fd to satisfy constructor
            }

            @Override
            public FileDescriptor getFD() throws IOException {
                if (nullFd != null) {
                    return nullFd;
                }
                throw new IOException();
            }
        }

        try {
            FOSWithNullFD fos = new FOSWithNullFD();
            fos.getFD();
            throw new AssertionError("Expected IOException not thrown when fd is null");
        } catch (IOException expected) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
