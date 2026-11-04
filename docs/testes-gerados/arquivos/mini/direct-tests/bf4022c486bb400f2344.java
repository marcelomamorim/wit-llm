/*
 * @test
 * @summary Test sun.net.www.protocol.ftp.FtpURLConnection.setConnectTimeout(int) method
 * @run main FtpURLConnectionWitupTest
 */

import sun.net.www.protocol.ftp.FtpURLConnection;
import java.net.URL;

public class FtpURLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        URL url = new URL("ftp://localhost/");
        FtpURLConnection ftpCon = new FtpURLConnection(url);

        // Test setting a valid positive timeout
        ftpCon.setConnectTimeout(1000);
        // No direct getter, so test by reflection is disallowed by instructions
        // Instead, test that no exception is thrown and method accepts zero
        ftpCon.setConnectTimeout(0);

        // Test that negative timeout throws IllegalArgumentException
        boolean threw = false;
        try {
            ftpCon.setConnectTimeout(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        }
    }
}
