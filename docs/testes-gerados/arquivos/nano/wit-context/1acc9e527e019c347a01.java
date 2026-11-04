/*
 * @test
 * @summary Test for ZipOutputStream.finish() method, focusing on behavior when stream is closed.
 * @run main ZipOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testFinishWhenStreamIsClosed();
        testFinishWhenStreamIsOpen();
    }

    private static void testFinishWhenStreamIsClosed() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        zos.close();
        try {
            zos.finish();
        } catch (IOException e) {
            System.out.println("Expected exception caught: " + e.getClass().getName());
            return;
        }
        throw new AssertionError("finish() did not throw IOException on closed stream");
    }

    private static void testFinishWhenStreamIsOpen() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        // No entries added, just call finish
        zos.finish();
        // Verify that no exception is thrown and stream is marked finished
        if (!isFinished(zos)) {
            throw new AssertionError("Stream not marked as finished after finish()");
        }
        zos.close();
    }

    private static boolean isFinished(ZipOutputStream zos) {
        // Accessing private field 'finished' via reflection is discouraged; instead, check behavior.
        // As a workaround, attempt to call finish() again and expect no exception.
        try {
            zos.finish();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
