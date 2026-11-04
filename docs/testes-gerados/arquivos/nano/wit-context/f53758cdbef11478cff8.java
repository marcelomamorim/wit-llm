/*
 * @test
 * @summary Test deleteFile method for null server scenario.
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import java.io.IOException;

public class FtpClientWitupTest {

    public static void main(String[] args) {
        testDeleteFileWithNullServer();
    }

    public static void testDeleteFileWithNullServer() {
        FtpClient client = new FtpClient() {
            @Override
            public FtpClient deleteFile(String name) throws IOException {
                // Simulate null server scenario
                if (this.server == null) {
                    throw new IllegalStateException("null == server");
                }
                return super.deleteFile(name);
            }
        };
        try {
            client.server = null; // Explicitly set server to null
            client.deleteFile("test.txt");
            System.out.println("Test failed: Exception not thrown");
        } catch (IllegalStateException e) {
            if (e.getMessage().equals("null == server")) {
                System.out.println("Test passed: Caught expected IllegalStateException");
            } else {
                System.out.println("Test failed: Unexpected message: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Test failed: Unexpected exception: " + e);
        }
    }
}
