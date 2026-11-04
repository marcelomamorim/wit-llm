/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.siteCmd(String) for IllegalStateException when server is null
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        // The WIT expath indicates that if server == null, siteCmd throws IllegalStateException
        // The current code calls issueCommandCheck which throws IllegalStateException if server == null
        // We test that calling siteCmd on a fresh client (server == null) throws IllegalStateException

        boolean threwIllegalStateException = false;
        try {
            client.siteCmd("HELP");
        } catch (IllegalStateException e) {
            threwIllegalStateException = true;
        } catch (FtpProtocolException | IOException e) {
            // These exceptions are declared but not expected here since server is null
            throw new RuntimeException("Unexpected exception", e);
        }

        if (!threwIllegalStateException) {
            throw new RuntimeException("Expected IllegalStateException when server is null");
        }

        // Additional sanity: siteCmd returns the same client instance
        // This is only reachable if no exception is thrown, so we skip here
    }
}
