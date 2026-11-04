/*
 * @test
 * @summary Test sun.net.www.protocol.mailto.MailToURLConnection.setReadTimeout(int) method
 * @run main MailToURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.mailto.MailToURLConnection;

public class MailToURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URL for constructor
        URL url = new URL("mailto:test@example.com");
        MailToURLConnection conn = new MailToURLConnection(url);

        // Test setting a valid positive timeout
        conn.setReadTimeout(1000);

        // Test setting zero timeout
        conn.setReadTimeout(0);

        // Test setting negative timeout throws IllegalArgumentException
        try {
            conn.setReadTimeout(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
