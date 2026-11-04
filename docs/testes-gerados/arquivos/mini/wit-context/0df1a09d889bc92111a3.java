/*
 * @test
 * @summary Test java.util.zip.InflaterOutputStream.flush() behavior including IOException on closed stream
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;
import java.util.zip.ZipException;

public class InflaterOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testFlushOnOpenStream();
        testFlushOnClosedStreamThrowsIOException();
    }

    private static void testFlushOnOpenStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(baos, inflater, 64);

        // flush on open stream should not throw IOException
        ios.flush();

        ios.close();
    }

    private static void testFlushOnClosedStreamThrowsIOException() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(baos, inflater, 64);

        try {
            ios.close();
            ios.flush();
            throw new AssertionError("Expected IOException when flushing closed InflaterOutputStream");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new AssertionError("IOException message mismatch or null: " + msg);
            }
        }
    }
}
