/*
 * @test
 * @summary Witup-derived checks for FileInputStream(FileDescriptor) constructor
 * @run main FileInputStreamWitupTest
 */

import java.io.FileDescriptor;
import java.io.FileInputStream;

public class FileInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullFileDescriptorThrowsNPE();
        testValidFileDescriptorIsKept();
    }

    static void testNullFileDescriptorThrowsNPE() {
        try {
            new FileInputStream((FileDescriptor) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null FileDescriptor");
    }

    static void testValidFileDescriptorIsKept() throws Exception {
        FileInputStream in = new FileInputStream(FileDescriptor.in);
        if (in.getFD() != FileDescriptor.in) {
            throw new RuntimeException("getFD() should return the FileDescriptor passed to the constructor");
        }
    }
}
