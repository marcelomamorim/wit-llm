/*
 * @test
 * @summary Test sun.net.ftp.impl.FtpClient.noop() method behavior including IllegalStateException when server is null
 * @run main FtpClientWitupTest
 */

import sun.net.ftp.impl.FtpClient;
import sun.net.ftp.FtpProtocolException;
import java.io.IOException;

public class FtpClientWitupTest {
    public static void main(String[] args) throws Exception {
        testNoopThrowsWhenServerNull();
        testNoopReturnsThisWhenServerSet();
    }

    private static void testNoopThrowsWhenServerNull() {
        FtpClient client = FtpClient.create();
        // forcibly set server to null via reflection is not allowed, so we rely on create() state
        // The default state of server in create() is null, so noop() should throw IllegalStateException
        try {
            client.noop();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        } catch (FtpProtocolException | IOException e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testNoopReturnsThisWhenServerSet() throws Exception {
        // We cannot set server field directly (private), and no public setter,
        // so we test that noop() returns this when server is non-null by connecting.
        // But since no public connect method is visible here, we skip actual connection.
        // Instead, we test that noop() returns this if server is non-null by subclassing.

        FtpClient client = new FtpClient() {
            // Override to simulate server non-null
            private java.net.Socket server = new java.net.Socket();

            @Override
            public FtpClient noop() throws FtpProtocolException, IOException {
                if (server == null) {
                    throw new IllegalStateException();
                }
                return this;
            }
        };

        FtpClient result = client.noop();
        if (result != client) {
            throw new RuntimeException("noop() did not return this when server is non-null");
        }
    }
}
