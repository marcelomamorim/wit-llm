/*
 * @test
 * @summary Tests for java.util.zip.InflaterOutputStream.finish()
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testFinishWithClosedStream();
        testFinishWithOpenStream();
    }

    private static void testFinishWithClosedStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(baos, inflater);
        try {
            // Close the stream to set the closed flag
            ios.close();
            // Attempt to call finish() after close, expecting IOException
            ios.finish();
            System.out.println("FAIL: Expected IOException when calling finish() after close.");
        } catch (IOException e) {
            if ("Stream closed".equals(e.getMessage())) {
                System.out.println("PASS: IOException thrown as expected when calling finish() after close.");
            } else {
                System.out.println("FAIL: Unexpected IOException message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    private static void testFinishWithOpenStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(baos, inflater);
        try {
            // Call finish() on an open stream
            ios.finish();
            System.out.println("PASS: finish() completed successfully on open stream.");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException during finish() on open stream: " + e);
        } finally {
            ios.close();
        }
    }
}
