/*
 * @test
 * @summary Test javax.net.ssl.SSLSocket.getHandshakeSession() throws UnsupportedOperationException
 * @run main SSLSocketWitupTest
 */

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSession;

public class SSLSocketWitupTest {
    public static void main(String[] args) {
        SSLSocket socket = new SSLSocket("localhost", 443) {
            // Use protected constructor with host and port
        };
        try {
            SSLSession session = socket.getHandshakeSession();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown, got: " + session);
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
