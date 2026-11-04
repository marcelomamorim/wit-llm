/*
 * @test
 * @summary Verifies FtpClient.changeDirectory rejects null and empty directories before issuing any command
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        testNullDirectory();
        testEmptyDirectory();
    }

    private static void testNullDirectory() throws Exception {
        FtpClient client = FtpClient.create();
        try {
            client.changeDirectory(null);
            throw new RuntimeException("Expected IllegalArgumentException for null directory");
        } catch (IllegalArgumentException e) {
            if (!"directory can't be null or empty".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }

    private static void testEmptyDirectory() throws Exception {
        FtpClient client = FtpClient.create();
        try {
            client.changeDirectory("");
            throw new RuntimeException("Expected IllegalArgumentException for empty directory");
        } catch (IllegalArgumentException e) {
            if (!"directory can't be null or empty".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
