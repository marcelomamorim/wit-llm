/*
 * @test
 * @summary Verify that MailToURLConnection.setReadTimeout rejects negative timeouts and accepts non-negative ones
 * @run main MailToURLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;

public class MailToURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URLConnection conn = new URL("mailto:user@example.com").openConnection();

        testNegativeTimeoutRejected(conn, -1);
        testNegativeTimeoutRejected(conn, Integer.MIN_VALUE);
        testValidTimeoutAccepted(conn, 0);
        testValidTimeoutAccepted(conn, 1);
        testValidTimeoutAccepted(conn, 5000);
        testValidTimeoutAccepted(conn, Integer.MAX_VALUE);
    }

    private static void testNegativeTimeoutRejected(URLConnection conn, int timeout) {
        try {
            conn.setReadTimeout(timeout);
        } catch (IllegalArgumentException e) {
            if (!"timeouts can't be negative".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for timeout " + timeout + ": " + e.getMessage());
            }
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for timeout " + timeout);
    }

    private static void testValidTimeoutAccepted(URLConnection conn, int timeout) {
        try {
            conn.setReadTimeout(timeout);
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Unexpected IllegalArgumentException for timeout " + timeout, e);
        }
    }
}
