/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.removeDirectory(String) for expected behavior and exception
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveDirectoryThrowsWhenServerNull();
        testRemoveDirectoryReturnsThis();
    }

    private static void testRemoveDirectoryThrowsWhenServerNull() {
        FtpClient client = FtpClient.create();
        // forcibly set server to null via reflection is not allowed, so we rely on create() state
        // The WIT expath expects IllegalStateException if server == null
        // The current source code calls issueCommandCheck which throws IllegalStateException if server == null
        // So we test that calling removeDirectory on a fresh client throws IllegalStateException
        try {
            client.removeDirectory("testdir");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        } catch (FtpProtocolException | IOException e) {
            throw new RuntimeException("Unexpected checked exception", e);
        }
    }

    private static void testRemoveDirectoryReturnsThis() throws Exception {
        // We cannot fully test successful removal without a real FTP server
        // But we can test that the method returns the same instance when no exception is thrown
        // To do this, we create a subclass that overrides issueCommandCheck to do nothing
        FtpClient client = new FtpClient() {
            @Override
            protected void issueCommandCheck(String cmd) {
                // do nothing to simulate success
            }
        };

        FtpClient result = client.removeDirectory("anydir");
        if (result != client) {
            throw new RuntimeException("removeDirectory did not return this");
        }
    }
}
