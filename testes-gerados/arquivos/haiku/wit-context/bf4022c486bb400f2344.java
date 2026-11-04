/*
 * @test
 * @summary Verify that FtpURLConnection.setConnectTimeout rejects negative timeouts and accepts non-negative ones
 * @modules java.base/sun.net.www.protocol.ftp
 * @run main FtpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        URL url = new URL("ftp://ftp.example.com/pub/test.txt");
        FtpURLConnection conn = new FtpURLConnection(url);

        int[] negatives = {-1, -100, Integer.MIN_VALUE};
        for (int t : negatives) {
            boolean thrown = false;
            try {
                conn.setConnectTimeout(t);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for timeout " + t);
            }
        }

        int[] valid = {0, 1, 5000, Integer.MAX_VALUE};
        for (int t : valid) {
            try {
                conn.setConnectTimeout(t);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Unexpected IllegalArgumentException for timeout " + t, e);
            }
        }

        conn.setConnectTimeout(5000);
        boolean thrownAfterValid = false;
        try {
            conn.setConnectTimeout(-1);
        } catch (IllegalArgumentException e) {
            thrownAfterValid = true;
        }
        if (!thrownAfterValid) {
            throw new RuntimeException("Expected IllegalArgumentException for timeout -1 after valid updates");
        }

        conn.setConnectTimeout(0);
    }
}
