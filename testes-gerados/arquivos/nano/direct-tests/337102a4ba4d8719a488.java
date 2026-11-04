/*
 * @test
 * @summary Test changeDirectory method of sun.net.ftp.impl.FtpClient
 * @run main FtpClientWitupTest
 */

import java.io.IOException;
import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) throws Exception {
        testChangeDirectoryValid();
        testChangeDirectoryNull();
        testChangeDirectoryEmpty();
    }

    private static void testChangeDirectoryValid() throws Exception {
        FtpClient client = createFtpClient();
        String dir = "testDir";
        try {
            client.changeDirectory(dir);
            System.out.println("changeDirectory with valid dir passed");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid dir", e);
        }
    }

    private static void testChangeDirectoryNull() throws Exception {
        FtpClient client = createFtpClient();
        try {
            client.changeDirectory(null);
            throw new AssertionError("Expected IllegalArgumentException for null directory");
        } catch (IllegalArgumentException e) {
            System.out.println("changeDirectory with null dir correctly threw IllegalArgumentException");
        }
    }

    private static void testChangeDirectoryEmpty() throws Exception {
        FtpClient client = createFtpClient();
        try {
            client.changeDirectory("");
            throw new AssertionError("Expected IllegalArgumentException for empty directory");
        } catch (IllegalArgumentException e) {
            System.out.println("changeDirectory with empty dir correctly threw IllegalArgumentException");
        }
    }

    private static FtpClient createFtpClient() {
        // Using the static factory method to create an instance
        return FtpClient.create();
    }
}
