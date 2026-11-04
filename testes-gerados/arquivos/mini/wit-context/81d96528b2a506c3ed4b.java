/*
 * @test
 * @summary Test java.util.Base64.DecInputStream.available() method for IOException when closed
 * @run main Base64WitupTest
 */

import java.io.IOException;
import java.util.Base64;
import java.io.InputStream;

public class Base64WitupTest {

    public static void main(String[] args) throws Exception {
        testAvailableThrowsWhenClosed();
        testAvailableReturnsAvailableWhenOpen();
    }

    private static void testAvailableThrowsWhenClosed() {
        Base64.Decoder decoder = Base64.getDecoder();
        // Create a DecInputStream by wrapping a closed InputStream to simulate closed state
        InputStream closedStream = new InputStream() {
            @Override
            public int read() throws IOException {
                throw new IOException("Stream closed");
            }
            @Override
            public int available() throws IOException {
                return 0;
            }
            @Override
            public void close() throws IOException {
                // do nothing
            }
        };

        Base64.Decoder.DecInputStream decInputStream = new Base64.Decoder.DecInputStream(closedStream);

        // Close the DecInputStream to set closed flag
        try {
            decInputStream.close();
        } catch (IOException e) {
            // ignore
        }

        boolean threw = false;
        try {
            decInputStream.available();
        } catch (IOException e) {
            threw = true;
            if (!"Stream is closed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
        if (!threw) {
            throw new RuntimeException("IOException was not thrown when calling available() on closed stream");
        }
    }

    private static void testAvailableReturnsAvailableWhenOpen() throws IOException {
        Base64.Decoder decoder = Base64.getDecoder();

        InputStream openStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
            @Override
            public int available() throws IOException {
                return 42;
            }
            @Override
            public void close() throws IOException {
                // do nothing
            }
        };

        Base64.Decoder.DecInputStream decInputStream = new Base64.Decoder.DecInputStream(openStream);

        int avail = decInputStream.available();
        if (avail != 42) {
            throw new RuntimeException("Expected available() to return 42 but got " + avail);
        }
    }
}
