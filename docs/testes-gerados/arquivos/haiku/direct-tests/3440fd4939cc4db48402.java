/*
 * @test
 * @summary Verify MailToURLConnection.setReadTimeout rejects negative timeouts and accepts non-negative values
 * @modules java.base/sun.net.www.protocol.mailto
 * @run main MailToURLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import sun.net.www.protocol.mailto.MailToURLConnection;

public class MailToURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URLConnection uc = new URL("mailto:user@example.com").openConnection();
        if (!(uc instanceof MailToURLConnection)) {
            throw new RuntimeException("Unexpected connection type: " + uc.getClass().getName());
        }
        MailToURLConnection c = (MailToURLConnection) uc;

        checkNegative(c, -1);
        checkNegative(c, -100);
        checkNegative(c, Integer.MIN_VALUE);

        c.setReadTimeout(0);
        c.setReadTimeout(1);
        c.setReadTimeout(5000);
        c.setReadTimeout(Integer.MAX_VALUE);

        checkNegative(c, -1);
        c.setReadTimeout(1000);
    }

    private static void checkNegative(MailToURLConnection c, int timeout) {
        try {
            c.setReadTimeout(timeout);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for timeout " + timeout);
    }
}
