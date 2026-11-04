/* @test    @summary Test setReadTimeout with valid and invalid values    @run main FtpURLConnectionWitupTest */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetReadTimeoutValid();
        testSetReadTimeoutNegative();
    }

    static void testSetReadTimeoutValid() {
        try {
            URL url = new URL("ftp://example.com/file.txt");
            FtpURLConnection conn = (FtpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            assert conn != null : "Connection should not be null after setting valid timeout";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid timeout test", e);
        }
    }

    static void testSetReadTimeoutNegative() {
        try {
            URL url = new URL("ftp://example.com/file.txt");
            FtpURLConnection conn = (FtpURLConnection) url.openConnection();
            conn.setReadTimeout(-100);
            throw new AssertionError("Expected IllegalArgumentException for negative timeout") ;
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in negative timeout test", e);
        }
    }
}
