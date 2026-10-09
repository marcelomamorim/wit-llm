/*
 * @test
 * @summary Test setting read timeout on HttpURLConnection
 * @run main HttpURLConnectionWitupTest_P3641a8c9fb90
 */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest_P3641a8c9fb90 {

    public static void main(String[] args) throws Exception {
        testSetReadTimeoutWithValidValue();
        testSetReadTimeoutWithZero();
        testSetReadTimeoutWithNegativeValue();
    }

    static void testSetReadTimeoutWithValidValue() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (String) null, 80);
        int timeout = 5000;
        conn.setReadTimeout(timeout);
        assert conn.readTimeout == timeout : "Timeout should be set to 5000";
    }

    static void testSetReadTimeoutWithZero() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (String) null, 80);
        int timeout = 0;
        conn.setReadTimeout(timeout);
        assert conn.readTimeout == timeout : "Timeout should be set to 0";
    }

    static void testSetReadTimeoutWithNegativeValue() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (String) null, 80);
        try {
            conn.setReadTimeout(-1);
            assert false : "IllegalArgumentException expected";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("timeouts can't be negative") : "Exception message mismatch";
        }
    }
}
