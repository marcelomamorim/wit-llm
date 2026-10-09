/* @test    @summary Test setRequestProperty method of HttpURLConnection for various scenarios    @run main HttpURLConnectionWitupTest_P315c21c74be1 */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest_P315c21c74be1 {

    public static void main(String[] args) throws Exception {
        testSetRequestPropertyBeforeConnect();
        testSetRequestPropertyNullKey();
        testSetRequestPropertyAfterConnect();
        testSetRequestPropertyNormal();
    }

    static void testSetRequestPropertyBeforeConnect() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (sun.net.www.protocol.http.Handler) null);
        conn.connected = false; // forcibly reset connection state
        conn.connecting = false;
        conn.setRequestProperty("Test-Header", "TestValue");
        assert "TestValue".equals(conn.requests.get("Test-Header")) : "Header not set correctly before connect";
        assert "TestValue".equals(conn.userHeaders.get("Test-Header")) : "User header not set correctly before connect";
    }

    static void testSetRequestPropertyNullKey() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (sun.net.www.protocol.http.Handler) null);
        try {
            conn.setRequestProperty(null, "value");
            assert false : "NullPointerException expected for null key";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testSetRequestPropertyAfterConnect() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (sun.net.www.protocol.http.Handler) null);
        conn.connected = true; // simulate connected state
        try {
            conn.setRequestProperty("Header", "Value");
            assert false : "IllegalStateException expected after connect";
        } catch (IllegalStateException e) {
            // expected
        }
    }

    static void testSetRequestPropertyNormal() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (sun.net.www.protocol.http.Handler) null);
        conn.connected = false;
        conn.connecting = false;
        conn.setRequestProperty("Custom-Header", "CustomValue");
        assert "CustomValue".equals(conn.requests.get("Custom-Header")) : "Header not set correctly";
        assert "CustomValue".equals(conn.userHeaders.get("Custom-Header")) : "User header not set correctly";
    }
}
