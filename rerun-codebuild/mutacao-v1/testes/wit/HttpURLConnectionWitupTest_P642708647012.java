/* @test    @summary Test setReadTimeout with valid and invalid values    @run main HttpURLConnectionWitupTest_P642708647012 */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest_P642708647012 {

    public static void main(String[] args) throws Exception {
        testSetReadTimeoutValid();
        testSetReadTimeoutNegative();
    }

    private static void testSetReadTimeoutValid() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (String) null, 80);
        try {
            conn.setReadTimeout(1000);
            if (conn.readTimeout != 1000) {
                throw new AssertionError("Expected readTimeout to be 1000, but was " + conn.readTimeout);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid timeout", e);
        }
    }

    private static void testSetReadTimeoutNegative() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = new HttpURLConnection(url, (String) null, 80);
        boolean exceptionThrown = false;
        try {
            conn.setReadTimeout(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative timeout");
        }
    }
}
