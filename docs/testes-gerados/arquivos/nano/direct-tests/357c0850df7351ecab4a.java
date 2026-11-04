/* @test    @summary Test FileOutputStream constructor with FileDescriptor parameter    @run main FileOutputStreamWitupTest */

import java.io.FileDescriptor;
import java.io.FileOutputStream;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidFileDescriptor();
        testConstructorWithNullFileDescriptor();
    }

    private static void testConstructorWithValidFileDescriptor() {
        FileDescriptor fd = new FileDescriptor();
        try {
            FileOutputStream fos = new FileOutputStream(fd);
            if (fos == null) {
                throw new AssertionError("FileOutputStream instance is null");
            }
            fos.close();
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testConstructorWithNullFileDescriptor() {
        try {
            new FileOutputStream((FileDescriptor) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
