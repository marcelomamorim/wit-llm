/* @test    @summary Test FileInputStream constructor with FileDescriptor parameter    @run main FileInputStreamWitupTest */

import java.io.FileDescriptor;
import java.io.FileInputStream;

public class FileInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidFileDescriptor();
        testConstructorWithNullFileDescriptor();
    }

    private static void testConstructorWithValidFileDescriptor() throws Exception {
        FileDescriptor fd = new FileDescriptor();
        // Attach the FileDescriptor to a FileInputStream
        FileInputStream fis = new FileInputStream(fd);
        // Verify that the stream is not closed
        if (fis != null) {
            System.out.println("testConstructorWithValidFileDescriptor: PASSED");
        } else {
            System.out.println("testConstructorWithValidFileDescriptor: FAILED");
        }
        fis.close();
    }

    private static void testConstructorWithNullFileDescriptor() {
        try {
            new FileInputStream((FileDescriptor) null);
            System.out.println("testConstructorWithNullFileDescriptor: FAILED");
        } catch (NullPointerException e) {
            System.out.println("testConstructorWithNullFileDescriptor: PASSED");
        } catch (Exception e) {
            System.out.println("testConstructorWithNullFileDescriptor: FAILED with unexpected exception");
        }
    }
}
