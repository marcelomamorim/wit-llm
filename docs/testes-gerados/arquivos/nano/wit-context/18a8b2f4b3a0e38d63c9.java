/*
 * @test
 * @summary Test for setReadTimeout method in FtpURLConnection
 * @run main FtpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetReadTimeoutNegative();
        testSetReadTimeoutZero();
        testSetReadTimeoutPositive();
    }

    public static void testSetReadTimeoutNegative() {
        try {
            URL url = new URL("ftp://example.com/file");
            FtpURLConnection conn = (FtpURLConnection) url.openConnection();
            conn.setReadTimeout(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative timeout");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testSetReadTimeoutZero() {
        try {
            URL url = new URL("ftp://example.com/file");
            FtpURLConnection conn = (FtpURLConnection) url.openConnection();
            conn.setReadTimeout(0);
            System.out.println("PASS: Successfully set timeout to zero");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testSetReadTimeoutPositive() {
        try {
            URL url = new URL("ftp://example.com/file");
            FtpURLConnection conn = (FtpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            System.out.println("PASS: Successfully set timeout to positive value");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
