/*
 * @test    @summary Test removeDirectory method of sun.net.ftp.impl.FtpClient
 * @run main FtpClientRemoveDirectoryTest
 */

import java.io.IOException;
import sun.net.ftp.impl.FtpClient;

public class FtpClientRemoveDirectoryTest {

    public static void main(String[] args) {
        testRemoveDirectorySuccess();
        testRemoveDirectoryThrowsFtpProtocolException();
    }

    private static void testRemoveDirectorySuccess() {
        FtpClient ftpClient = new FtpClient() {
            @Override
            public FtpClient issueCommandCheck(String command) throws IOException {
                if (command.startsWith("RMD ")) {
                    // Simulate successful removal
                    return this;
                }
                throw new IOException("Unexpected command: " + command);
            }
        };
        try {
            FtpClient result = ftpClient.removeDirectory("testDir");
            if (result != null) {
                System.out.println("testRemoveDirectorySuccess passed");
            } else {
                System.err.println("testRemoveDirectorySuccess failed: result is null");
            }
        } catch (Exception e) {
            System.err.println("testRemoveDirectorySuccess failed with exception: " + e);
        }
    }

    private static void testRemoveDirectoryThrowsFtpProtocolException() {
        FtpClient ftpClient = new FtpClient() {
            @Override
            public FtpClient issueCommandCheck(String command) throws IOException {
                if (command.startsWith("RMD ")) {
                    throw new IOException("FtpProtocolException");
                }
                return this;
            }
        };
        try {
            ftpClient.removeDirectory("testDir");
            System.err.println("testRemoveDirectoryThrowsFtpProtocolException failed: exception not thrown");
        } catch (IOException e) {
            if (e.getMessage().contains("FtpProtocolException")) {
                System.out.println("testRemoveDirectoryThrowsFtpProtocolException passed");
            } else {
                System.err.println("testRemoveDirectoryThrowsFtpProtocolException failed with unexpected exception: " + e);
            }
        } catch (Exception e) {
            System.err.println("testRemoveDirectoryThrowsFtpProtocolException failed with unexpected exception: " + e);
        }
    }
}
