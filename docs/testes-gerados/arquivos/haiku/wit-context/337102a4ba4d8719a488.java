/*
 * @test
 * @summary Witup-generated checks for FtpClient.changeDirectory argument validation and unconnected behavior
 * @modules java.base/sun.net.ftp
 *          java.base/sun.net.ftp.impl
 * @run main FtpClientWitupTest
 */

import java.io.IOException;
import sun.net.ftp.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();
        expectIllegalArgument(client, null);
        expectIllegalArgument(client, "");
        expectFailureOnUnconnectedClient(client, "/tmp");
    }

    private static void expectIllegalArgument(FtpClient client, String dir) {
        try {
            client.changeDirectory(dir);
            throw new RuntimeException("Expected IllegalArgumentException for: " + dir);
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for invalid argument", e);
        }
    }

    private static void expectFailureOnUnconnectedClient(FtpClient client, String dir) {
        try {
            client.changeDirectory(dir);
            throw new RuntimeException("Expected failure on unconnected client");
        } catch (IllegalStateException e) {
            // expected
        } catch (IOException e) {
            // FtpProtocolException extends IOException; accepted as a clean failure
        }
    }
}
