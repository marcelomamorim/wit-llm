/*
 * @test
 * @summary Verify that HttpURLConnection.setReadTimeout rejects negative timeouts and accepts non-negative values
 * @modules java.base/sun.net.www.protocol.http
 * @run main HttpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost/");
        HttpURLConnection conn = new HttpURLConnection(url, "localhost", 80);

        testNegativeTimeoutRejected(conn);
        testMinIntegerTimeoutRejected(conn);
        testZeroTimeoutAccepted(conn);
        testPositiveTimeoutAccepted(conn);
        testMaxIntegerTimeoutAccepted(conn);
    }

    static void testNegativeTimeoutRejected(HttpURLConnection conn) {
        boolean thrown = false;
        try {
            conn.setReadTimeout(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for timeout -1");
        }
    }

    static void testMinIntegerTimeoutRejected(HttpURLConnection conn) {
        boolean thrown = false;
        try {
            conn.setReadTimeout(Integer.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for Integer.MIN_VALUE");
        }
    }

    static void testZeroTimeoutAccepted(HttpURLConnection conn) {
        conn.setReadTimeout(0);
        if (conn.getReadTimeout() != 0) {
            throw new RuntimeException("Expected read timeout 0, got " + conn.getReadTimeout());
        }
    }

    static void testPositiveTimeoutAccepted(HttpURLConnection conn) {
        conn.setReadTimeout(5000);
        if (conn.getReadTimeout() != 5000) {
            throw new RuntimeException("Expected read timeout 5000, got " + conn.getReadTimeout());
        }
    }

    static void testMaxIntegerTimeoutAccepted(HttpURLConnection conn) {
        conn.setReadTimeout(Integer.MAX_VALUE);
        if (conn.getReadTimeout() != Integer.MAX_VALUE) {
            throw new RuntimeException("Expected read timeout Integer.MAX_VALUE, got " + conn.getReadTimeout());
        }
    }
}
