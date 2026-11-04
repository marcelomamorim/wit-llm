/* @test    @summary Test for FileOutputStream.getFD() method    @run main FileOutputStreamWitupTest */

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testGetFDWithValidFileDescriptor();
        testGetFDWithNullFileDescriptor();
    }

    private static void testGetFDWithValidFileDescriptor() throws IOException {
        FileDescriptor fd = new FileDescriptor();
        FileOutputStream fos = new FileOutputStream(fd);
        FileDescriptor resultFd = fos.getFD();
        assert resultFd != null : "Expected non-null FileDescriptor";
        assert resultFd == fd : "Returned FileDescriptor should be the same as the one provided";
        fos.close();
    }

    private static void testGetFDWithNullFileDescriptor() {
        try {
            // Creating a FileOutputStream with a null FileDescriptor is not directly possible via constructor,
            // so simulate by subclassing or reflection if needed. Here, we test the behavior when fd is null.
            // Since fd is private and final, we cannot set it to null directly; instead, test the method's behavior
            // by subclassing and overriding getFD() to return null, but as per constraints, we avoid reflection.
            // Alternatively, test the behavior when fd is null by creating a subclass that overrides getFD().
            class NullFDFileOutputStream extends FileOutputStream {
                public NullFDFileOutputStream() {
                    super();
                }
                @Override
                public final FileDescriptor getFD() throws IOException {
                    return null;
                }
            }
            NullFDFileOutputStream fos = new NullFDFileOutputStream();
            fos.getFD();
            assert false : "Expected IOException when getFD() returns null";
        } catch (IOException e) {
            // Expected exception
        }
    }
}
