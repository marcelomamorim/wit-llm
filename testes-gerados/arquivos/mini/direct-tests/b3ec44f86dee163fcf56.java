/*
 * @test
 * @summary Test InflaterOutputStream.finish() behavior
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testFinishOnOpenStream();
        testFinishClosesDefaultInflater();
        testFinishThrowsOnClosedStream();
    }

    private static void testFinishOnOpenStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(baos, new Inflater(), 64);
        ios.finish(); // should not throw
        ios.close();
    }

    private static void testFinishClosesDefaultInflater() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(baos); // uses default inflater
        ios.finish(); // should call inf.end() without error
        ios.close();
    }

    private static void testFinishThrowsOnClosedStream() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InflaterOutputStream ios = new InflaterOutputStream(baos);
            ios.close();
            ios.finish();
            throw new AssertionError("Expected IOException on finish() after close()");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new AssertionError("Unexpected IOException message: " + msg);
            }
        }
    }
}
