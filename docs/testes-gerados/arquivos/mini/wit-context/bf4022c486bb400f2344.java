/*
 * @test
 * @summary Test sun.net.www.protocol.ftp.FtpURLConnection.setConnectTimeout(int) for valid and invalid inputs
 * @run main FtpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        URL url = new URL("ftp://localhost/");
        FtpURLConnection conn = new FtpURLConnection(url);

        // Test valid timeout values
        conn.setConnectTimeout(0);
        conn.setConnectTimeout(1000);

        // Test invalid timeout value: negative should throw IllegalArgumentException
        boolean threw = false;
        try {
            conn.setConnectTimeout(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        }
    }
}
