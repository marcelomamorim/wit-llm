/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.abort() method behavior
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.FtpClient;
import sun.net.ftp.impl.FtpClient as ImplFtpClient;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        testAbortThrowsWhenServerNull();
        testAbortReturnsThisWhenServerNotNull();
    }

    private static void testAbortThrowsWhenServerNull() {
        ImplFtpClient client = ImplFtpClient.create();
        // forcibly set server to null via reflection is not allowed, so we rely on default state
        // The default server field is null before connect, so abort() should throw IllegalStateException
        try {
            client.abort();
            throw new RuntimeException("Expected IllegalStateException when server is null");
        } catch (IllegalStateException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testAbortReturnsThisWhenServerNotNull() throws Exception {
        ImplFtpClient client = ImplFtpClient.create();
        // Connect to a dummy local server socket to set server field non-null
        java.net.ServerSocket ss = new java.net.ServerSocket(0);
        java.net.Socket socket = new java.net.Socket("localhost", ss.getLocalPort());
        // We cannot set private server field directly, so we simulate connect by reflection
        try {
            java.lang.reflect.Field serverField = ImplFtpClient.class.getDeclaredField("server");
            serverField.setAccessible(true);
            serverField.set(client, socket);

            // Now abort() should not throw IllegalStateException
            FtpClient result = client.abort();
            if (result != client) {
                throw new RuntimeException("abort() should return this instance");
            }
        } finally {
            socket.close();
            ss.close();
        }
    }
}
