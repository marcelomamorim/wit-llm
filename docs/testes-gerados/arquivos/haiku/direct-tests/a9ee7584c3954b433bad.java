/*
 * @test
 * @summary Verify MailToURLConnection.setConnectTimeout rejects negative timeouts and accepts non-negative ones
 * @modules java.base/sun.net.www.protocol.mailto
 * @run main MailToURLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import sun.net.www.protocol.mailto.MailToURLConnection;

public class MailToURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        MailToURLConnection c = newConnection();

        expectIllegalArgument(c, -1);
        expectIllegalArgument(c, -100);
        expectIllegalArgument(c, Integer.MIN_VALUE);

        c.setConnectTimeout(0);
        c.setConnectTimeout(1);
        c.setConnectTimeout(5000);
        c.setConnectTimeout(Integer.MAX_VALUE);

        MailToURLConnection c2 = newConnection();
        c2.setConnectTimeout(0);
        expectIllegalArgument(c2, -5);
    }

    private static MailToURLConnection newConnection() throws Exception {
        URL url = new URL("mailto:test@example.com");
        URLConnection uc = url.openConnection();
        if (!(uc instanceof MailToURLConnection)) {
            throw new RuntimeException("Unexpected connection type: " + uc.getClass());
        }
        return (MailToURLConnection) uc;
    }

    private static void expectIllegalArgument(MailToURLConnection c, int timeout) {
        try {
            c.setConnectTimeout(timeout);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for timeout " + timeout);
    }
}
