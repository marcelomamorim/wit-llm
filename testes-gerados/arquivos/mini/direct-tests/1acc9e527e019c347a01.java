/*
 * @test
 * @summary Test ZipOutputStream.finish() behavior including idempotency and state transitions
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testFinishIdempotent();
        testFinishWithoutEntries();
        testFinishWithSingleEntry();
        testFinishAfterCloseThrows();
    }

    // Test that finish() can be called multiple times without error and does not write extra data
    private static void testFinishIdempotent() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        ZipEntry entry = new ZipEntry("test.txt");
        zos.putNextEntry(entry);
        byte[] data = "hello".getBytes("UTF-8");
        zos.write(data);
        zos.closeEntry();

        zos.finish();
        byte[] firstFinish = baos.toByteArray();

        zos.finish(); // second call should be no-op
        byte[] secondFinish = baos.toByteArray();

        if (firstFinish.length != secondFinish.length) {
            throw new AssertionError("finish() is not idempotent: output length changed on second call");
        }
    }

    // Test finish() on a ZipOutputStream with no entries
    private static void testFinishWithoutEntries() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        zos.finish();
        byte[] output = baos.toByteArray();

        if (output.length == 0) {
            throw new AssertionError("finish() did not write central directory for empty zip");
        }
    }

    // Test finish() after adding a single entry and writing data
    private static void testFinishWithSingleEntry() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        ZipEntry entry = new ZipEntry("file.txt");
        zos.putNextEntry(entry);
        byte[] content = "content".getBytes("UTF-8");
        zos.write(content);
        zos.closeEntry();

        zos.finish();
        byte[] output = baos.toByteArray();

        if (output.length == 0) {
            throw new AssertionError("finish() did not write output for single entry zip");
        }
    }

    // Test that finish() throws IOException if stream is closed
    private static void testFinishAfterCloseThrows() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        zos.close();

        boolean threw = false;
        try {
            zos.finish();
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("closed")) {
                throw new AssertionError("IOException thrown by finish() after close does not mention 'closed'");
            }
        }
        if (!threw) {
            throw new AssertionError("finish() did not throw IOException after stream was closed");
        }
    }
}
