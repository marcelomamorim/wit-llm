/*
 * @test
 * @summary Test javax.net.ssl.SSLSocket.getHandshakeApplicationProtocolSelector throws UnsupportedOperationException
 * @run main SSLSocketWitupTest
 */

import javax.net.ssl.SSLSocket;

public class SSLSocketWitupTest {
    public static void main(String[] args) {
        SSLSocket socket = new SSLSocket("localhost", 443) {
            // Provide minimal implementation to allow instantiation
            @Override
            public void startHandshake() {
                // no-op
            }
        };

        try {
            socket.getHandshakeApplicationProtocolSelector();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
