/*
 * @test
 * @summary Test javax.net.ssl.SSLSocket.getHandshakeSession() throws UnsupportedOperationException
 * @run main javax.net.ssl.SSLSocketWitupTest
 */

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSession;

public class SSLSocketWitupTest {
    public static void main(String[] args) throws Exception {
        SSLSocket socket = new SSLSocket() { };
        try {
            SSLSession session = socket.getHandshakeSession();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown, got: " + session);
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
