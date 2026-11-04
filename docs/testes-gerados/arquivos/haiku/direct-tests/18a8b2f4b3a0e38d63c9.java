/*
 * @test
 * @summary Verify FtpURLConnection.setReadTimeout rejects negative values and accepts zero and positive values
 * @modules java.base/sun.net.www.protocol.ftp
 * @run main FtpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static FtpURLConnection newConnection() throws Exception {
        URL url = new URL("ftp://localhost/test.txt");
        return new FtpURLConnection(url);
    }

    static void testNegativeTimeoutThrows(int value) throws Exception {
        FtpURLConnection con = newConnection();
        boolean thrown = false;
        try {
            con.setReadTimeout(value);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "setReadTimeout(" + value + ") should throw IllegalArgumentException");
    }

    static void testNonNegativeTimeoutAccepted(int value) throws Exception {
        FtpURLConnection con = newConnection();
        boolean thrown = false;
        try {
            con.setReadTimeout(value);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(!thrown, "setReadTimeout(" + value + ") should not throw");
    }

    public static void main(String[] args) throws Exception {
        testNegativeTimeoutThrows(-1);
        testNegativeTimeoutThrows(-1000);
        testNegativeTimeoutThrows(Integer.MIN_VALUE);

        testNonNegativeTimeoutAccepted(0);
        testNonNegativeTimeoutAccepted(1);
        testNonNegativeTimeoutAccepted(5000);
        testNonNegativeTimeoutAccepted(Integer.MAX_VALUE);

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
