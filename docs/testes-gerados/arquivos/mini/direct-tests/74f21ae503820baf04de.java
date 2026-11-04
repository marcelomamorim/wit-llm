/*
 * @test
 * @summary Test sun.net.www.protocol.http.HttpURLConnection.addRequestProperty method
 * @run main HttpURLConnectionWitupTest
 */

import sun.net.www.protocol.http.HttpURLConnection;
import java.net.URL;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.InetSocketAddress;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testAddRequestPropertyNormal();
        testAddRequestPropertyAfterConnected();
        testAddRequestPropertyNullKey();
        testAddRequestPropertyContentTypeHeader();
    }

    private static void testAddRequestPropertyNormal() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, Proxy.NO_PROXY);

        // Add a normal header
        conn.addRequestProperty("X-Test-Header", "value1");

        // The header should be present in requests and userHeaders (except Content-Type)
        String val = conn.requests.get("X-Test-Header");
        if (!"value1".equals(val)) {
            throw new RuntimeException("Expected header value 'value1' but got: " + val);
        }
        String userVal = conn.userHeaders.get("X-Test-Header");
        if (!"value1".equals(userVal)) {
            throw new RuntimeException("Expected user header value 'value1' but got: " + userVal);
        }
    }

    private static void testAddRequestPropertyAfterConnected() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, Proxy.NO_PROXY);

        // Simulate connected state
        conn.connected = true;

        try {
            conn.addRequestProperty("X-Test", "value");
            throw new RuntimeException("Expected IllegalStateException when adding header after connected");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    private static void testAddRequestPropertyNullKey() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, Proxy.NO_PROXY);

        try {
            conn.addRequestProperty(null, "value");
            throw new RuntimeException("Expected NullPointerException when key is null");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testAddRequestPropertyContentTypeHeader() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, Proxy.NO_PROXY);

        conn.addRequestProperty("Content-Type", "text/plain");

        // requests should contain Content-Type
        String val = conn.requests.get("Content-Type");
        if (!"text/plain".equals(val)) {
            throw new RuntimeException("Expected Content-Type header value 'text/plain' but got: " + val);
        }

        // userHeaders should NOT contain Content-Type
        String userVal = conn.userHeaders.get("Content-Type");
        if (userVal != null) {
            throw new RuntimeException("userHeaders should not contain Content-Type header");
        }
    }
}
