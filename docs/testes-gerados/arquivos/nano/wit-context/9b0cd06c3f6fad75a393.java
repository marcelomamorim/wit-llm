/* @test    @summary Test for FileInputStream(FileDescriptor) constructor handling null argument    @run main FileInputStreamWitupTest */

import java.io.FileDescriptor;
import java.io.FileInputStream;

public class FileInputStreamWitupTest {

    public static void main(String[] args) {
        testNullFileDescriptor();
        testValidFileDescriptor();
    }

    private static void testNullFileDescriptor() {
        try {
            new FileInputStream((FileDescriptor) null);
            System.err.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testValidFileDescriptor() {
        try {
            FileDescriptor fd = new FileDescriptor();
            new FileInputStream(fd);
            System.out.println("PASS: Created FileInputStream with valid FileDescriptor");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }
}
