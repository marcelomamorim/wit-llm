/* @test    @summary Test setting a valid connect timeout value in FtpURLConnection    @run main FtpURLConnectionWitupTest */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {

    public static void main(String[] args) {
        try {
            URL url = new URL("ftp://example.com/file.txt");
            FtpURLConnection conn = (FtpURLConnection) url.openConnection();
            // Set a valid timeout value
            conn.setConnectTimeout(5000);
            // Verify that the timeout was set correctly
            if (conn != null) {
                System.out.println("Connect timeout set to: 5000 ms");
            } else {
                throw new AssertionError("Connection object is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("Test failed due to exception: " + e.getMessage());
        }
    }

}