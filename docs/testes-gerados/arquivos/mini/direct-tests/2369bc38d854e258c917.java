/*
 * @test
 * @summary Test that getHandshakeApplicationProtocolSelector throws UnsupportedOperationException
 * @run main javax.net.ssl.SSLSocketWitupTest
 */

import javax.net.ssl.SSLSocket;

public class SSLSocketWitupTest {
    public static void main(String[] args) {
        SSLSocket socket = new SSLSocket() { };
        try {
            socket.getHandshakeApplicationProtocolSelector();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
