/*
 * @test
 * @summary Test addRequestProperty method of HttpURLConnection for various scenarios
 * @run main HttpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testAddRequestPropertyBeforeConnect();
        testAddRequestPropertyNullKey();
        testAddRequestPropertyAfterConnect();
        testAddRequestPropertyNormal();
    }

    static void testAddRequestPropertyBeforeConnect() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (sun.net.www.protocol.http.Handler) null);
        conn.connected = false; // forcibly set to false for test
        conn.connecting = false;
        conn.requests = new java.util.HashMap<>();
        conn.userHeaders = new java.util.HashMap<>();
        conn.addRequestProperty("Key1", "Value1");
        assert "Value1".equals(conn.requests.get("Key1"));
        assert "Value1".equals(conn.userHeaders.get("Key1"));
    }

    static void testAddRequestPropertyNullKey() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (sun.net.www.protocol.http.Handler) null);
        conn.connected = false;
        conn.connecting = false;
        try {
            conn.addRequestProperty(null, "Value");
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testAddRequestPropertyAfterConnect() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (sun.net.www.protocol.http.Handler) null);
        conn.connected = true;
        conn.connecting = false;
        try {
            conn.addRequestProperty("Key", "Value");
            assert false : "Expected IllegalStateException";
        } catch (IllegalStateException e) {
            // expected
        }
    }

    static void testAddRequestPropertyNormal() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (sun.net.www.protocol.http.Handler) null);
        conn.connected = false;
        conn.connecting = false;
        conn.requests = new java.util.HashMap<>();
        conn.userHeaders = new java.util.HashMap<>();
        conn.addRequestProperty("Content-Type", "application/json");
        assert "application/json".equals(conn.requests.get("Content-Type"));
        assert "application/json".equals(conn.userHeaders.get("Content-Type"));
    }
}
