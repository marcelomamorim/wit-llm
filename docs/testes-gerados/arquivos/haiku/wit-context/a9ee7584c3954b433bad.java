/*
 * @test
 * @summary Verify that MailToURLConnection.setConnectTimeout rejects negative timeouts and accepts non-negative ones
 * @modules java.base/sun.net.www.protocol.mailto
 * @run main MailToURLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import sun.net.www.protocol.mailto.MailToURLConnection;

public class MailToURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        MailToURLConnection c = (MailToURLConnection) new URL("mailto:test@example.com").openConnection();

        expectIllegalArgument(c, -1);
        expectIllegalArgument(c, Integer.MIN_VALUE);

        c.setConnectTimeout(0);
        c.setConnectTimeout(1);
        c.setConnectTimeout(1000);
        c.setConnectTimeout(Integer.MAX_VALUE);
    }

    private static void expectIllegalArgument(MailToURLConnection c, int timeout) {
        try {
            c.setConnectTimeout(timeout);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for timeout " + timeout);
    }
}
