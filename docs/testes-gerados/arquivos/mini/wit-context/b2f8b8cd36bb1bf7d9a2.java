/*
 * @test
 * @summary Test DeflaterInputStream.reset() throws IOException as expected
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal InputStream that never returns data but does not close
        InputStream dummyIn = new InputStream() {
            public int read() {
                return -1; // EOF immediately
            }
        };

        DeflaterInputStream dis = new DeflaterInputStream(dummyIn);

        try {
            dis.reset();
            throw new RuntimeException("Expected IOException not thrown by reset()");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("mark/reset not supported")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
        }

        // Also test that close does not interfere with reset exception
        dis.close();
        try {
            dis.reset();
            throw new RuntimeException("Expected IOException not thrown by reset() after close");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("mark/reset not supported")) {
                throw new RuntimeException("IOException thrown but message unexpected after close: " + msg);
            }
        }
    }
}
