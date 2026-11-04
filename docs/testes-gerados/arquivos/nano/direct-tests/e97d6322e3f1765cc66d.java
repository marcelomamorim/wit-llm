/* @test    @summary Test setReadTimeout method of HttpURLConnection for valid and invalid inputs    @run main HttpURLConnectionWitupTest */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetReadTimeoutValid();
        testSetReadTimeoutNegative();
    }

    static void testSetReadTimeoutValid() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (String) null, 80);
        conn.setReadTimeout(5000);
        if (conn.readTimeout != 5000) {
            throw new AssertionError("Expected readTimeout to be 5000, but was " + conn.readTimeout);
        }
    }

    static void testSetReadTimeoutNegative() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (String) null, 80);
        try {
            conn.setReadTimeout(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
