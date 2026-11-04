/*
 * @test
 * @summary Test FileOutputStream.getFD() method behavior
 * @run main FileOutputStreamWitupTest
 */

import java.io.FileOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.File;

public class FileOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testGetFDReturnsNonNull();
    }

    private static void testGetFDReturnsNonNull() throws IOException {
        File tempFile = null;
        FileOutputStream fos = null;
        try {
            tempFile = File.createTempFile("foswitup", ".tmp");
            fos = new FileOutputStream(tempFile);
            FileDescriptor fd = fos.getFD();
            if (fd == null) {
                throw new AssertionError("getFD() returned null");
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
