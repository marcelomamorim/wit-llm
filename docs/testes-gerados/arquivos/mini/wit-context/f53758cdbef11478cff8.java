/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.deleteFile(String) for IllegalStateException when server is null
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        FtpClient client = FtpClient.create();

        // The WIT expath suggests IllegalStateException if server == null
        // The current code calls issueCommandCheck which throws IllegalStateException if server == null
        // We test that calling deleteFile on a fresh client (server not connected) throws IllegalStateException

        boolean threwIllegalStateException = false;
        try {
            client.deleteFile("somefile.txt");
        } catch (IllegalStateException e) {
            threwIllegalStateException = true;
        }

        if (!threwIllegalStateException) {
            throw new RuntimeException("Expected IllegalStateException when server is null");
        }
    }
}
