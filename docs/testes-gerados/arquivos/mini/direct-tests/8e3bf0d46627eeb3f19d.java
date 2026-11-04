/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.setRestartOffset(long) method
 * @run main sun.net.ftp.impl.FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        // Test setting a valid positive offset
        client.setRestartOffset(0L);
        client.setRestartOffset(12345L);

        // Test setting offset to Long.MAX_VALUE
        client.setRestartOffset(Long.MAX_VALUE);

        // Test that negative offset throws IllegalArgumentException
        try {
            client.setRestartOffset(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative offset");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            client.setRestartOffset(Long.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for negative offset");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
