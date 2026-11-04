/*
 * @test
 * @summary Test sun.net.www.protocol.ftp.FtpURLConnection.setReadTimeout(int) for valid and invalid inputs
 * @run main FtpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        // Construct a valid URL for FTP
        URL url = new URL("ftp://localhost/");
        FtpURLConnection conn = new FtpURLConnection(url);

        // Test setting a valid read timeout
        conn.setReadTimeout(1000);
        // No direct getter, so we rely on no exception and internal state change

        // Test setting zero timeout (boundary case)
        conn.setReadTimeout(0);

        // Test setting a negative timeout triggers IllegalArgumentException
        boolean threw = false;
        try {
            conn.setReadTimeout(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        }

        System.out.println("All tests passed.");
    }
}
