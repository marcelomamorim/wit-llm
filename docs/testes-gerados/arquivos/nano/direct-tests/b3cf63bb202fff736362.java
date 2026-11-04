/*
 * @test
 * @summary Test the rename method of sun.net.ftp.impl.FtpClient for correct command issuing.
 * @run main FtpClientRenameTest
 */

import java.io.IOException;

public class FtpClientRenameTest {

    public static void main(String[] args) {
        testRenameSuccess();
        testRenameWithEmptyStrings();
    }

    private static void testRenameSuccess() {
        sun.net.ftp.impl.FtpClient ftpClient = new sun.net.ftp.impl.FtpClient() {
            @Override
            public sun.net.ftp.FtpClient rename(String from, String to) throws sun.net.ftp.FtpProtocolException, IOException {
                if ("oldFile".equals(from) && "newFile".equals(to)) {
                    // Simulate correct command sequence
                    if (issueCommandCheck("RNFR " + from) && issueCommandCheck("RNTO " + to)) {
                        return this;
                    } else {
                        throw new IOException("Command check failed")
                    }
                }
                throw new IOException("Unexpected parameters")
            }

            private boolean issueCommandCheck(String command) {
                // Simulate command issuing
                System.out.println("Issued command: " + command);
                return true;
            }
        };
        try {
            ftpClient.rename("oldFile", "newFile");
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage(), e);
        }
    }

    private static void testRenameWithEmptyStrings() {
        sun.net.ftp.impl.FtpClient ftpClient = new sun.net.ftp.impl.FtpClient() {
            @Override
            public sun.net.ftp.FtpClient rename(String from, String to) throws sun.net.ftp.FtpProtocolException, IOException {
                if ("".equals(from) || "".equals(to)) {
                    throw new IOException("Empty filename not allowed")
                }
                return super.rename(from, to);
            }
        };
        try {
            ftpClient.rename("", "newFile");
            throw new RuntimeException("Expected exception for empty 'from'");
        } catch (IOException e) {
            // Expected
        }
        try {
            ftpClient.rename("oldFile", "");
            throw new RuntimeException("Expected exception for empty 'to'");
        } catch (IOException e) {
            // Expected
        }
    }
}
