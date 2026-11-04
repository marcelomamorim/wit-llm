/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.rename method for expected behavior and exception
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;
import sun.net.ftp.impl.FtpClient as ImplFtpClient;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        testRenameThrowsOnNullServer();
        testRenameNormalBehavior();
    }

    private static void testRenameThrowsOnNullServer() {
        ImplFtpClient client = new ImplFtpClient() {
            // Override to simulate null server socket causing IllegalStateException
            @Override
            protected void issueCommandCheck(String cmd) throws IOException {
                if (server == null) {
                    throw new IllegalStateException("server is null");
                }
            }
        };
        // forcibly set server to null to simulate condition
        try {
            java.lang.reflect.Field serverField = ImplFtpClient.class.getDeclaredField("server");
            serverField.setAccessible(true);
            serverField.set(client, null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Reflection failed", e);
        }

        boolean thrown = false;
        try {
            client.rename("oldName", "newName");
        } catch (IllegalStateException e) {
            thrown = true;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException not thrown when server is null");
        }
    }

    private static void testRenameNormalBehavior() throws Exception {
        ImplFtpClient client = ImplFtpClient.create();
        // We cannot connect to a real server, so we test that rename returns the client instance
        // without throwing if issueCommandCheck does not throw
        FtpClient result = client.rename("fromFile", "toFile");
        if (result != client) {
            throw new RuntimeException("rename did not return this instance");
        }
    }
}
