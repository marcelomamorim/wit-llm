/*
 * @test
 * @summary Test sun.net.www.protocol.http.HttpURLConnection.read(byte[] b) method
 * @run main HttpURLConnectionWitupTest
 */

import sun.net.www.protocol.http.HttpURLConnection;
import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testReadWithValidBuffer();
        testReadWithEmptyBuffer();
        testReadWithNullBuffer();
    }

    private static void testReadWithValidBuffer() throws Exception {
        // Create a dummy HttpURLConnection instance with a valid URL and Proxy.NO_PROXY
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, Proxy.NO_PROXY) {
            // Override read(byte[], int, int) to simulate reading data
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                if (b == null) throw new NullPointerException();
                if (len == 0) return 0;
                // Simulate reading len bytes
                int toRead = Math.min(len, 5);
                for (int i = off; i < off + toRead; i++) {
                    b[i] = (byte) ('A' + i - off);
                }
                return toRead;
            }
        };

        byte[] buffer = new byte[10];
        int readCount = conn.read(buffer);
        if (readCount <= 0 || readCount > buffer.length) {
            throw new RuntimeException("read returned invalid count: " + readCount);
        }
        // Check that buffer is filled correctly in the first readCount bytes
        for (int i = 0; i < readCount; i++) {
            if (buffer[i] != (byte) ('A' + i)) {
                throw new RuntimeException("buffer content mismatch at index " + i);
            }
        }
    }

    private static void testReadWithEmptyBuffer() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, Proxy.NO_PROXY) {
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return 0; // simulate no bytes read
            }
        };

        byte[] buffer = new byte[0];
        int readCount = conn.read(buffer);
        if (readCount != 0) {
            throw new RuntimeException("Expected 0 bytes read for empty buffer, got " + readCount);
        }
    }

    private static void testReadWithNullBuffer() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, Proxy.NO_PROXY) {
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                if (b == null) throw new NullPointerException();
                return 0;
            }
        };

        try {
            conn.read(null);
            throw new RuntimeException("Expected NullPointerException for null buffer");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
