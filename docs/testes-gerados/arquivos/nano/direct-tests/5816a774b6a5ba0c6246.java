/* @test    @summary Test getFD() method of FileInputStream for valid and invalid cases    @run main FileInputStreamWitupTest */

import java.io.FileInputStream;
import java.io.FileDescriptor;
import java.io.IOException;

public class FileInputStreamWitupTest {

    public static void main(String[] args) {
        testGetFDWithValidFileDescriptor();
        testGetFDWithNullFd();
    }

    private static void testGetFDWithValidFileDescriptor() {
        try {
            // Create a FileInputStream with a valid file descriptor
            // Using a dummy FileDescriptor for testing purposes
            FileDescriptor fd = new FileDescriptor();
            FileInputStream fis = new FileInputStream(fd);
            FileDescriptor resultFd = fis.getFD();
            assert resultFd != null : "Expected non-null FileDescriptor";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testGetFDWithValidFileDescriptor", e);
        }
    }

    private static void testGetFDWithNullFd() {
        try {
            // Create a subclass that returns null for fd to simulate null case
            FileInputStream fis = new FileInputStream() {
                @Override
                public final FileDescriptor getFD() throws IOException {
                    return null;
                }
            };
            // Call getFD(), expecting an IOException
            fis.getFD();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
