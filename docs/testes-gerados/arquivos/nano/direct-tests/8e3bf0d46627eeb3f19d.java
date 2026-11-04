/*
 * @test
 * @summary Test setRestartOffset method for FtpClient.
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) {
        testSetRestartOffsetValid();
        testSetRestartOffsetNegative();
    }

    static void testSetRestartOffsetValid() {
        FtpClient client = new FtpClient();
        long offset = 1024L;
        FtpClient result = client.setRestartOffset(offset);
        if (result != client) {
            throw new AssertionError("Expected same instance to be returned");
        }
        System.out.println("testSetRestartOffsetValid passed");
    }

    static void testSetRestartOffsetNegative() {
        FtpClient client = new FtpClient();
        boolean exceptionThrown = false;
        try {
            client.setRestartOffset(-1L);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative offset");
        }
        System.out.println("testSetRestartOffsetNegative passed");
    }
}
