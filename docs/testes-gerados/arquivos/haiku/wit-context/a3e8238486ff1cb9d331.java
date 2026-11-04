/*
 * @test
 * @summary Smoke test for MailToURLConnection.getOutputStream reached through the public URL API
 * @run main MailToURLConnectionWitupTest
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MailToURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("mailto:user@example.com");
        if (!"mailto".equals(url.getProtocol())) {
            throw new AssertionError("unexpected protocol: " + url.getProtocol());
        }
        URLConnection conn = url.openConnection();
        if (conn == null) {
            throw new AssertionError("openConnection returned null");
        }
        try {
            OutputStream os = conn.getOutputStream();
            if (os == null) {
                throw new AssertionError("getOutputStream returned null");
            }
        } catch (IOException expected) {
            // No SMTP server may be reachable in the test environment.
        }
    }
}
