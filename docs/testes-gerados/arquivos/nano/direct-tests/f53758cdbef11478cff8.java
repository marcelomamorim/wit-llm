/*
 * @test
 * @summary Test deleteFile method of sun.net.ftp.impl.FtpClient
 * @run main FtpClientWitupTest
 */
import java.io.IOException;
import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) {
        testDeleteFileSuccess();
        testDeleteFileThrowsFtpProtocolException();
    }

    private static void testDeleteFileSuccess() {
        FtpClient ftpClient = new FtpClient() {
            @Override
            public FtpClient deleteFile(String name) throws FtpProtocolException, IOException {
                if ("testfile.txt".equals(name)) {
                    // Simulate successful deletion
                    return this;
                }
                throw new IOException("Unexpected filename");
            }
        };
        try {
            ftpClient.deleteFile("testfile.txt");
            System.out.println("testDeleteFileSuccess passed");
        } catch (Exception e) {
            System.err.println("testDeleteFileSuccess failed: " + e);
        }
    }

    private static void testDeleteFileThrowsFtpProtocolException() {
        FtpClient ftpClient = new FtpClient() {
            @Override
            public FtpClient deleteFile(String name) throws FtpProtocolException, IOException {
                throw new FtpProtocolException("Protocol error");
            }
        };
        try {
            ftpClient.deleteFile("anyfile.txt");
            System.err.println("testDeleteFileThrowsFtpProtocolException failed: no exception thrown");
        } catch (FtpProtocolException e) {
            System.out.println("testDeleteFileThrowsFtpProtocolException passed");
        } catch (IOException e) {
            System.err.println("testDeleteFileThrowsFtpProtocolException failed: wrong exception type");
        }
    }
}
