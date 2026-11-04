/*
 * @test
 * @summary Test java.util.zip.InflaterOutputStream.finish() behavior including IOException on closed stream
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testFinishThrowsOnClosedStream();
        testFinishNormalBehavior();
    }

    private static void testFinishThrowsOnClosedStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(baos);
        try {
            ios.close();
            ios.finish();
            throw new RuntimeException("Expected IOException not thrown on finish() after close()");
        } catch (IOException e) {
            // Expected exception due to closed stream
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new RuntimeException("IOException message mismatch: " + msg);
            }
        }
    }

    private static void testFinishNormalBehavior() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(baos);
        // Call finish without closing - should not throw
        ios.finish();
        // After finish, stream should still be open, so write should succeed
        ios.write(new byte[] {0, 1, 2});
        ios.flush();
        ios.close();
    }
}
