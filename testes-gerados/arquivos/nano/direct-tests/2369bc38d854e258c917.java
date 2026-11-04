/* @test    @summary Test getHandshakeApplicationProtocolSelector method of SSLSocket    @run main SSLSocketWitupTest */

import javax.net.ssl.SSLSocket;

public class SSLSocketWitupTest {

    public static void main(String[] args) {
        testGetHandshakeApplicationProtocolSelector();
    }

    public static void testGetHandshakeApplicationProtocolSelector() {
        SSLSocket socket = new SSLSocket() {
            @Override
            public Object getHandshakeApplicationProtocolSelector() {
                return "test-selector";
            }
        };
        Object result = socket.getHandshakeApplicationProtocolSelector();
        if (result == null || !result.equals("test-selector")) {
            throw new AssertionError("Expected 'test-selector', but got: " + result);
        }
    }
}
