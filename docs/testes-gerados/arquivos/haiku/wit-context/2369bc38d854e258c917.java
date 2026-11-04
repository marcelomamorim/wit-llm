/*
 * @test
 * @summary Verify that SSLSocket.getHandshakeApplicationProtocolSelector() throws UnsupportedOperationException in the current checkout
 * @run main SSLSocketWitupTest
 */

import javax.net.ssl.SSLSocket;

public class SSLSocketWitupTest {

    static class TestSocket extends SSLSocket {
        TestSocket() {
            super();
        }
    }

    public static void main(String[] args) throws Exception {
        TestSocket socket = new TestSocket();
        boolean thrown = false;
        try {
            socket.getHandshakeApplicationProtocolSelector();
        } catch (UnsupportedOperationException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from getHandshakeApplicationProtocolSelector()");
        }
    }
}
