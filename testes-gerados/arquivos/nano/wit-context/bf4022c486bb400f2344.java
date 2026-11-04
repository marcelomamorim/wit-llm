/*
 * @test
 * @summary Test for setConnectTimeout method in FtpURLConnection
 * @run main FtpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {

    public static void main(String[] args) {
        testSetConnectTimeoutNegative();
        testSetConnectTimeoutZero();
        testSetConnectTimeoutPositive();
    }

    public static void testSetConnectTimeoutNegative() {
        try {
            FtpURLConnection conn = new FtpURLConnection(new URL("ftp://example.com"));
            conn.setConnectTimeout(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative timeout");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testSetConnectTimeoutZero() {
        try {
            FtpURLConnection conn = new FtpURLConnection(new URL("ftp://example.com"));
            conn.setConnectTimeout(0);
            System.out.println("PASS: setConnectTimeout(0) executed without exception");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for zero timeout: " + e);
        }
    }

    public static void testSetConnectTimeoutPositive() {
        try {
            FtpURLConnection conn = new FtpURLConnection(new URL("ftp://example.com"));
            conn.setConnectTimeout(1000);
            System.out.println("PASS: setConnectTimeout(1000) executed without exception");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for positive timeout: " + e);
        }
    }
}
