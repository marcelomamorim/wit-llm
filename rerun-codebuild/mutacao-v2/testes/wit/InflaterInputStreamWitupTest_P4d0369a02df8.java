/*
 * @test
 * @summary Test java.util.zip.InflaterInputStream.reset() throws IOException
 * @run main InflaterInputStreamWitupTest_P4d0369a02df8
 */

import java.util.zip.InflaterInputStream;
import java.util.zip.Inflater;
import java.io.InputStream;
import java.io.IOException;

public class InflaterInputStreamWitupTest_P4d0369a02df8 {
    public static void main(String[] args) throws Exception {
        testResetThrowsIOException();
    }

    private static void testResetThrowsIOException() {
        InflaterInputStream iis = new InflaterInputStream(new InputStream() {
            public int read() throws IOException {
                return -1; // EOF immediately
            }
        }, new Inflater());

        try {
            iis.reset();
            throw new AssertionError("Expected IOException not thrown by reset()");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("mark/reset not supported")) {
                throw new AssertionError("IOException message unexpected: " + msg);
            }
        }
    }
}
