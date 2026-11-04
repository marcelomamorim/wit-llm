/*
 * @test
 * @summary Test ZipOutputStream.finish() behavior including IOException on closed stream
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testFinishOnOpenStream();
        testFinishOnClosedStreamThrows();
    }

    private static void testFinishOnOpenStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        // finish() on open stream should complete without exception
        zos.finish();
        // calling finish() again should be idempotent and not throw
        zos.finish();
        zos.close();
    }

    private static void testFinishOnClosedStreamThrows() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        try {
            zos.close();
            zos.finish();
            throw new RuntimeException("Expected IOException not thrown on finish() after close()");
        } catch (IOException e) {
            // Expected IOException due to closed stream
        }
    }
}
