/*
 * @test
 * @summary Test setReadTimeout with valid and invalid inputs
 * @run main HttpURLConnectionWitupTest_P27fdafefe1d5
 */

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionWitupTest_P27fdafefe1d5 {

    public static void main(String[] args) throws Exception {
        testSetReadTimeoutValid();
        testSetReadTimeoutNegative();
    }

    static void testSetReadTimeoutValid() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(1000);
        assert conn.getReadTimeout() == 1000 : "Expected readTimeout to be 1000";
    }

    static void testSetReadTimeoutNegative() throws Exception {
        URL url = new URL("http://example.com");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            conn.setReadTimeout(-1);
            assert false : "Expected IllegalArgumentException for negative timeout";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("timeouts can't be negative") : "Unexpected exception message";
        }
    }
}
