/*
 * @test
 * @summary Verify that FtpURLConnection.setReadTimeout rejects negative timeouts and accepts non-negative ones
 * @modules java.base/sun.net.www.protocol.ftp
 * @run main FtpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        FtpURLConnection c = new FtpURLConnection(new URL("ftp://localhost/test.txt"));

        expectNegativeRejected(c, Integer.MIN_VALUE);
        expectNegativeRejected(c, -1);

        c.setReadTimeout(0);
        c.setReadTimeout(1);
        c.setReadTimeout(Integer.MAX_VALUE);
    }

    private static void expectNegativeRejected(FtpURLConnection c, int timeout) {
        try {
            c.setReadTimeout(timeout);
        } catch (IllegalArgumentException e) {
            if (!"timeouts can't be negative".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage(), e);
            }
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for timeout " + timeout);
    }
}
