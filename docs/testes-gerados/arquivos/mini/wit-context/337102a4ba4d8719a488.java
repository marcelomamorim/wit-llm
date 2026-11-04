/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.changeDirectory(String) for argument validation and state
 * @run main sun.net.ftp.impl.FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        testChangeDirectoryNullOrEmpty();
        testChangeDirectoryWithoutConnection();
    }

    private static void testChangeDirectoryNullOrEmpty() {
        FtpClient client = FtpClient.create();
        try {
            client.changeDirectory(null);
            throw new RuntimeException("Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null argument", e);
        }

        try {
            client.changeDirectory("");
            throw new RuntimeException("Expected IllegalArgumentException for empty string argument");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for empty string argument", e);
        }
    }

    private static void testChangeDirectoryWithoutConnection() {
        FtpClient client = FtpClient.create();
        // We do not connect the client, so server socket is null internally
        // The method should throw IllegalStateException when server is null
        try {
            client.changeDirectory("/someDir");
            throw new RuntimeException("Expected IllegalStateException when server socket is null");
        } catch (IllegalStateException e) {
            // expected
        } catch (FtpProtocolException | IOException e) {
            // These exceptions are declared but not expected here
            throw new RuntimeException("Unexpected checked exception", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}
