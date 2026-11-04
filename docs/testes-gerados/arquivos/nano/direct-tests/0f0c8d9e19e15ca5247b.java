/* @test    @summary Test getHandshakeSession() method of SSLSocket for UnsupportedOperationException    @run main SSLSocketWitupTest */

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSession;

public class SSLSocketWitupTest {

    public static void main(String[] args) {
        testGetHandshakeSessionThrows();
    }

    private static void testGetHandshakeSessionThrows() {
        SSLSocket socket = new SSLSocket() {
            @Override
            public SSLSession getHandshakeSession() {
                throw new UnsupportedOperationException();
            }
        };
        try {
            socket.getHandshakeSession();
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
        }
    }
}
