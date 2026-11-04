/*
 * @test
 * @summary FtpURLConnection.setConnectTimeout accepts non-negative values and rejects negative values
 * @modules java.base/sun.net.www.protocol.ftp
 * @run main FtpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL("ftp://localhost/test.txt");
        FtpURLConnection conn = new FtpURLConnection(url);

        conn.setConnectTimeout(0);
        check(conn.getConnectTimeout() == 0, "zero timeout not stored");

        conn.setConnectTimeout(5000);
        check(conn.getConnectTimeout() == 5000, "positive timeout not stored");

        boolean thrown = false;
        try {
            conn.setConnectTimeout(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "negative timeout must throw IllegalArgumentException");

        check(conn.getConnectTimeout() == 5000,
              "timeout must be unchanged after rejected negative value");
    }
}
