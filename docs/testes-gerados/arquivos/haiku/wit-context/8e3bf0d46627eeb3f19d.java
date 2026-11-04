/*
 * @test
 * @summary Verifies FtpClient.setRestartOffset rejects negative offsets and accepts non-negative offsets, returning the same client instance
 * @modules java.base/sun.net.ftp
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        boolean thrown = false;
        try {
            client.setRestartOffset(-1L);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "negative offset -1 must throw IllegalArgumentException");

        thrown = false;
        try {
            client.setRestartOffset(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "Long.MIN_VALUE offset must throw IllegalArgumentException");

        check(client.setRestartOffset(0L) == client, "setRestartOffset(0) must return the same client");
        check(client.setRestartOffset(1024L) == client, "setRestartOffset(1024) must return the same client");
        check(client.setRestartOffset(Long.MAX_VALUE) == client, "setRestartOffset(Long.MAX_VALUE) must return the same client");
    }
}
