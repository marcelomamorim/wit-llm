/*
 * @test
 * @summary Smoke test for MailToURLConnection.getOutputStream() reached through the public URL.openConnection() API
 * @run main MailToURLConnectionWitupTest
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class MailToURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        System.setProperty("user.fromaddr", "sender@example.com");

        URL url = new URL("mailto:recipient@example.com");
        URLConnection conn = url.openConnection();

        if (!"mailto".equals(conn.getURL().getProtocol())) {
            throw new RuntimeException("Unexpected protocol: " + conn.getURL().getProtocol());
        }

        try {
            OutputStream os = conn.getOutputStream();
            if (os == null) {
                throw new RuntimeException("getOutputStream() returned null");
            }
            OutputStream again = conn.getOutputStream();
            if (again != os) {
                throw new RuntimeException("getOutputStream() must return the cached stream");
            }
        } catch (IOException e) {
            // No reachable SMTP server in this environment; the IOException path is acceptable.
        }
    }
}
