/*
 * @test
 * @summary Test setConnectTimeout with valid and invalid inputs
 * @run main HttpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetConnectTimeoutValid();
        testSetConnectTimeoutNegative();
    }

    static void testSetConnectTimeoutValid() {
        try {
            URL url = new URL("http://example.com");
            HttpURLConnection conn = new HttpURLConnection(url, (String) null, 80);
            conn.setConnectTimeout(1000);
            if (conn.getConnectTimeout() != 1000) {
                throw new AssertionError("Expected connectTimeout to be 1000, but was " + conn.getConnectTimeout());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid timeout test", e);
        }
    }

    static void testSetConnectTimeoutNegative() {
        try {
            URL url = new URL("http://example.com");
            HttpURLConnection conn = new HttpURLConnection(url, (String) null, 80);
            conn.setConnectTimeout(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in negative timeout test", e);
        }
    }
}
