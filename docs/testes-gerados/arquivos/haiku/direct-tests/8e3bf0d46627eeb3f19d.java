/*
 * @test
 * @summary Verify FtpClient.setRestartOffset returns the same client and rejects negative offsets
 * @modules java.base/sun.net.ftp
 * @modules java.base/sun.net.ftp.impl
 * @run main FtpClientWitupTest
 */

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        sun.net.ftp.FtpClient client = sun.net.ftp.FtpClient.create();

        if (client.setRestartOffset(0L) != client) {
            throw new RuntimeException("setRestartOffset(0) must return this client");
        }

        if (client.setRestartOffset(1024L) != client) {
            throw new RuntimeException("setRestartOffset(1024) must return this client");
        }

        if (client.setRestartOffset(Long.MAX_VALUE) != client) {
            throw new RuntimeException("setRestartOffset(Long.MAX_VALUE) must return this client");
        }

        boolean thrown = false;
        try {
            client.setRestartOffset(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Negative offset must throw IllegalArgumentException");
        }

        thrown = false;
        try {
            client.setRestartOffset(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Long.MIN_VALUE offset must throw IllegalArgumentException");
        }

        if (client.setRestartOffset(5L) != client) {
            throw new RuntimeException("Client must remain usable after rejected negative offset");
        }
    }
}
