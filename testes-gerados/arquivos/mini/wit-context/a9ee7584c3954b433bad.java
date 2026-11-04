/*
 * @test
 * @summary Test sun.net.www.protocol.mailto.MailToURLConnection.setConnectTimeout(int) for valid and invalid inputs
 * @run main MailToURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.mailto.MailToURLConnection;
import java.lang.IllegalArgumentException;

public class MailToURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy URL for constructor
        URL url = new URL("mailto:test@example.com");
        MailToURLConnection conn = new MailToURLConnection(url);

        // Test valid positive timeout
        conn.setConnectTimeout(1000);

        // Test zero timeout
        conn.setConnectTimeout(0);

        // Test negative timeout triggers IllegalArgumentException
        boolean threw = false;
        try {
            conn.setConnectTimeout(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("negative")) {
                throw new RuntimeException("Exception message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        }
    }
}
