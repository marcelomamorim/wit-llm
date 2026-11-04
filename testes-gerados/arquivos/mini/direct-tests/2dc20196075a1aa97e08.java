/*
 * @test
 * @summary Test sun.net.www.protocol.http.HttpURLConnection.setConnectTimeout(int) method
 * @run main HttpURLConnectionWitupTest
 */

import sun.net.www.protocol.http.HttpURLConnection;
import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;

public class HttpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URL for constructor
        URL url = new URL("http://example.com");
        Proxy proxy = Proxy.NO_PROXY;

        // Use protected constructor with URL and Proxy and null handler (allowed by signature)
        HttpURLConnection conn = new HttpURLConnection(url, proxy) {
            @Override
            public void disconnect() {}
            @Override
            public boolean usingProxy() { return false; }
            @Override
            public void connect() {}
        };

        // Test valid timeout values
        conn.setConnectTimeout(0);
        conn.setConnectTimeout(1000);

        // Test negative timeout throws IllegalArgumentException
        try {
            conn.setConnectTimeout(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test large positive timeout
        conn.setConnectTimeout(Integer.MAX_VALUE);
    }
}
