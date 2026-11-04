/*
 * @test
 * @summary Verify FileOutputStream(FileDescriptor) null check and basic construction
 * @run main FileOutputStreamWitupTest
 */

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullFileDescriptorThrowsNPE();
        testValidFileDescriptorCreatesStream();
    }

    static void testNullFileDescriptorThrowsNPE() {
        boolean thrown = false;
        try {
            new FileOutputStream((FileDescriptor) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null FileDescriptor");
        }
    }

    static void testValidFileDescriptorCreatesStream() {
        FileDescriptor fd = new FileDescriptor();
        FileOutputStream fos = new FileOutputStream(fd);
        if (fos == null) {
            throw new RuntimeException("Constructor returned null");
        }
        if (!(fos instanceof OutputStream)) {
            throw new RuntimeException("Stream is not an OutputStream");
        }
    }
}
