/*
 * @test
 * @summary Test sun.net.www.protocol.http.HttpURLConnection.setReadTimeout(int) for valid and invalid inputs
 * @run main HttpURLConnectionWitupTest
 */

import sun.net.www.protocol.http.HttpURLConnection;
import java.net.URL;
import java.net.Proxy;
import java.net.MalformedURLException;

public class HttpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Construct a HttpURLConnection instance using public constructor
        URL url = new URL("http://example.com");
        Proxy proxy = Proxy.NO_PROXY;
        HttpURLConnection conn = new HttpURLConnection(url, proxy);

        // Test valid timeout values
        conn.setReadTimeout(0); // zero timeout allowed
        if (conn.getReadTimeout() != 0) {
            throw new RuntimeException("Expected readTimeout to be 0");
        }

        conn.setReadTimeout(1000); // positive timeout
        if (conn.getReadTimeout() != 1000) {
            throw new RuntimeException("Expected readTimeout to be 1000");
        }

        // Test invalid timeout value: negative should throw IllegalArgumentException
        boolean threw = false;
        try {
            conn.setReadTimeout(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        }
    }
}
