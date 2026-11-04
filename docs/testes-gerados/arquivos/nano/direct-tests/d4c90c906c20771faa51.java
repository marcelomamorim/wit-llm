/*
 * @test    @summary Test makeDirectory method of sun.net.ftp.impl.FtpClient
 * @run main FtpClientWitupTest
 */
import java.io.IOException;
import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        testMakeDirectorySuccess();
        testMakeDirectoryException();
    }

    private static void testMakeDirectorySuccess() throws Exception {
        FtpClient ftpClient = new FtpClient();
        FtpClient spyClient = new FtpClient() {
            @Override
            public FtpClient issueCommandCheck(String command) throws IOException {
                if (command.startsWith("MKD ")) {
                    return this;
                }
                throw new IOException("Unexpected command: " + command);
            }
        };
        try {
            spyClient.makeDirectory("testDir");
        } catch (Exception e) {
            throw new AssertionError("Expected no exception, but got: " + e);
        }
    }

    private static void testMakeDirectoryException() throws Exception {
        FtpClient ftpClient = new FtpClient();
        FtpClient spyClient = new FtpClient() {
            @Override
            public FtpClient issueCommandCheck(String command) throws IOException {
                throw new IOException("Simulated IO exception");
            }
        };
        boolean exceptionThrown = false;
        try {
            spyClient.makeDirectory("failDir");
        } catch (IOException e) {
            exceptionThrown = true;
            if (!e.getMessage().contains("Simulated IO exception")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException to be thrown");
        }
    }
}
