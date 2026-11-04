/* @test    @summary Test for FileOutputStream(FileDescriptor fdObj) constructor, focusing on null input.    @run main FileOutputStreamWitupTest */

import java.io.FileDescriptor;
import java.io.FileOutputStream;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) {
        testNullFileDescriptor();
        testValidFileDescriptor();
    }

    private static void testNullFileDescriptor() {
        try {
            new FileOutputStream((FileDescriptor) null);
            System.err.println("Failed: Expected NullPointerException for null FileDescriptor");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null FileDescriptor");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null FileDescriptor: " + e);
        }
    }

    private static void testValidFileDescriptor() {
        try {
            FileDescriptor fd = new FileDescriptor();
            new FileOutputStream(fd);
            System.out.println("Passed: FileOutputStream created with valid FileDescriptor");
        } catch (Exception e) {
            System.err.println("Failed: Exception thrown with valid FileDescriptor: " + e);
        }
    }
}
