/*
 * @test
 * @summary Test sun.net.www.protocol.ftp.FtpURLConnection.setReadTimeout(int) method
 * @run main FtpURLConnectionWitupTest
 */

import sun.net.www.protocol.ftp.FtpURLConnection;
import java.net.URL;

public class FtpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        URL url = new URL("ftp://localhost/");
        FtpURLConnection con = new FtpURLConnection(url);

        // Test setting a valid positive timeout
        con.setReadTimeout(1000);
        // No direct getter, so test by setting again to another value
        con.setReadTimeout(0);

        // Test setting zero timeout (allowed)
        con.setReadTimeout(0);

        // Test setting negative timeout throws IllegalArgumentException
        boolean threw = false;
        try {
            con.setReadTimeout(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        }
    }
}
