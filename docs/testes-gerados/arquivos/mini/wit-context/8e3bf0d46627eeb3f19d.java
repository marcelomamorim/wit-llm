/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.setRestartOffset(long) for valid and invalid inputs
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        // Test valid offset (0)
        FtpClient returned = client.setRestartOffset(0L);
        if (returned != client) {
            throw new RuntimeException("setRestartOffset should return this instance");
        }

        // Test valid positive offset
        returned = client.setRestartOffset(12345L);
        if (returned != client) {
            throw new RuntimeException("setRestartOffset should return this instance");
        }

        // Test invalid negative offset triggers IllegalArgumentException
        boolean threw = false;
        try {
            client.setRestartOffset(-1L);
        } catch (IllegalArgumentException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("offset can't be negative")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative offset");
        }
    }
}
