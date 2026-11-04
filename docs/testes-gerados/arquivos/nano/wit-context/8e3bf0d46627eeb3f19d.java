/*
 * @test
 * @summary Test for setRestartOffset method in sun.net.ftp.impl.FtpClient
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) {
        testSetRestartOffsetNegative();
        testSetRestartOffsetZero();
        testSetRestartOffsetPositive();
    }

    private static void testSetRestartOffsetNegative() {
        FtpClient client = createFtpClient();
        try {
            client.setRestartOffset(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative offset");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative offset");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testSetRestartOffsetZero() {
        FtpClient client = createFtpClient();
        try {
            FtpClient result = client.setRestartOffset(0);
            if (result == client) {
                System.out.println("PASS: setRestartOffset(0) returned the same instance");
            } else {
                System.out.println("FAIL: setRestartOffset(0) did not return the same instance");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for zero offset: " + e);
        }
    }

    private static void testSetRestartOffsetPositive() {
        FtpClient client = createFtpClient();
        try {
            FtpClient result = client.setRestartOffset(12345);
            if (result == client) {
                System.out.println("PASS: setRestartOffset(12345) returned the same instance");
            } else {
                System.out.println("FAIL: setRestartOffset(12345) did not return the same instance");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for positive offset: " + e);
        }
    }

    private static FtpClient createFtpClient() {
        return FtpClient.create();
    }
}
