/*
 * @test
 * @summary Test sun.net.www.protocol.http.HttpURLConnection.read(byte[]) method for IOException on closed stream
 * @run main HttpURLConnectionWitupTest
 */

import sun.net.www.protocol.http.HttpURLConnection;
import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;
import java.io.IOException;

public class HttpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URL
        URL url = new URL("http://example.com");

        // Use public constructor with URL and Proxy.NO_PROXY
        HttpURLConnection conn = new HttpURLConnection(url, Proxy.NO_PROXY) {
            // Override connect to do nothing
            public void connect() throws IOException {}
            // Override getInputStream to simulate closed stream
            public java.io.InputStream getInputStream() throws IOException {
                throw new IOException("Stream closed");
            }
        };

        // Prepare a byte array for reading
        byte[] buffer = new byte[10];

        // Since the real read(byte[]) calls read(byte[], int, int),
        // and the underlying stream is closed, expect IOException
        try {
            conn.read(buffer);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            // Expected exception due to closed stream
            if (!"Stream closed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
