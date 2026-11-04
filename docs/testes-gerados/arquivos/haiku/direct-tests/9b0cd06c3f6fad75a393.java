/*
 * @test
 * @summary FileInputStream(FileDescriptor) must reject null and accept a valid descriptor without reading from it
 * @run main FileInputStreamWitupTest
 */

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullDescriptorThrowsNpe();
        testStandardInputDescriptorConstructsStream();
        System.out.println("PASSED");
    }

    static void testNullDescriptorThrowsNpe() {
        boolean thrown = false;
        try {
            new FileInputStream((FileDescriptor) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null FileDescriptor");
        }
    }

    static void testStandardInputDescriptorConstructsStream() throws IOException {
        FileInputStream fis = new FileInputStream(FileDescriptor.in);
        if (fis == null) {
            throw new RuntimeException("Constructor returned null stream");
        }
        if (!(fis instanceof InputStream)) {
            throw new RuntimeException("Stream is not an InputStream");
        }
    }
}
