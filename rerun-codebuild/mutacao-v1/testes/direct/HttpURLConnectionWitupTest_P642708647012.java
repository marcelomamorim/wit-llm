/* @test    @summary Test setReadTimeout with valid and invalid values    @run main HttpURLConnectionWitupTest_P642708647012 */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest_P642708647012 {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://example.com");
            HttpURLConnection conn = new HttpURLConnection(url, (Proxy) null);
            // Test with valid timeout
            conn.setReadTimeout(1000);
            if (conn.readTimeout != 1000) {
                throw new AssertionError("Expected readTimeout to be 1000, but was " + conn.readTimeout);
            }
            // Test with zero timeout
            conn.setReadTimeout(0);
            if (conn.readTimeout != 0) {
                throw new AssertionError("Expected readTimeout to be 0, but was " + conn.readTimeout);
            }
            // Test with negative timeout, should throw IllegalArgumentException
            try {
                conn.setReadTimeout(-1);
                throw new AssertionError("Expected IllegalArgumentException for negative timeout");
            } catch (IllegalArgumentException e) {
                // expected
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
