/* @test    @summary Test sendUrgentData throws SocketException for BaseSSLSocketImpl    @run main BaseSSLSocketImplWitupTest */

import java.net.SocketException;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) {
        testSendUrgentDataThrows();
    }

    public static void testSendUrgentDataThrows() {
        sun.security.ssl.BaseSSLSocketImpl socket = new sun.security.ssl.BaseSSLSocketImpl() {
            // No additional implementation needed for this test
        };
        try {
            socket.sendUrgentData(1);
            throw new AssertionError("Expected SocketException was not thrown");
        } catch (SocketException se) {
            String message = se.getMessage();
            if (message == null || !message.startsWith("This method is not supported")) {
                throw new AssertionError("Unexpected exception message: " + message, se);
            }
        }
    }
}
