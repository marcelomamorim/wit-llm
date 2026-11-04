/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.changeDirectory method
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        testChangeDirectoryNull();
        testChangeDirectoryEmpty();
        testChangeDirectoryValid();
    }

    private static void testChangeDirectoryNull() {
        FtpClient client = new FtpClient();
        try {
            client.changeDirectory(null);
            throw new RuntimeException("Expected IllegalArgumentException for null directory");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null directory", e);
        }
    }

    private static void testChangeDirectoryEmpty() {
        FtpClient client = new FtpClient();
        try {
            client.changeDirectory("");
            throw new RuntimeException("Expected IllegalArgumentException for empty directory");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for empty directory", e);
        }
    }

    private static void testChangeDirectoryValid() throws Exception {
        FtpClient client = new FtpClient() {
            @Override
            protected void issueCommandCheck(String cmd) throws FtpProtocolException, IOException {
                if (!cmd.startsWith("CWD ")) {
                    throw new RuntimeException("Command does not start with CWD: " + cmd);
                }
                // simulate success
            }
        };

        FtpClient returned = client.changeDirectory("/validDir");
        if (returned != client) {
            throw new RuntimeException("changeDirectory should return this");
        }
    }
}