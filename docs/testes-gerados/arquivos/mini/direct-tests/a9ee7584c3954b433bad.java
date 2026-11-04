/*
 * @test
 * @summary Test setConnectTimeout method of MailToURLConnection for valid and invalid inputs
 * @run main MailToURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.mailto.MailToURLConnection;

public class MailToURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URL with mailto scheme
        URL url = new URL("mailto:test@example.com");
        MailToURLConnection conn = new MailToURLConnection(url);

        // Test valid timeout values
        conn.setConnectTimeout(0);
        conn.setConnectTimeout(1000);

        // Test negative timeout throws IllegalArgumentException
        try {
            conn.setConnectTimeout(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        try {
            conn.setConnectTimeout(Integer.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
