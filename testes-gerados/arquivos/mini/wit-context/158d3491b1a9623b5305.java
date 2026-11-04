/*
 * @test
 * @summary Test java.net.ServerSocket.setSocketFactory behavior and exception
 * @run main ServerSocketWitupTest
 */

import java.net.ServerSocket;
import java.net.SocketImplFactory;
import java.net.SocketException;
import java.io.IOException;

public class ServerSocketWitupTest {
    public static void main(String[] args) throws Exception {
        testSetSocketFactoryNull();
        testSetSocketFactoryTwiceThrows();
    }

    private static void testSetSocketFactoryNull() throws IOException {
        // Reset factory to null by reflection is not allowed, so we rely on fresh JVM state
        // This test sets factory to null (default) and then sets a factory successfully
        // We create a dummy factory to set
        SocketImplFactory dummyFactory = new SocketImplFactory() {
            public java.net.SocketImpl createSocketImpl() {
                return null; // minimal implementation for test
            }
        };
        // Set factory first time should succeed
        ServerSocket.setSocketFactory(dummyFactory);
    }

    private static void testSetSocketFactoryTwiceThrows() throws IOException {
        // The factory is already set by previous test, so setting again should throw SocketException
        SocketImplFactory anotherFactory = new SocketImplFactory() {
            public java.net.SocketImpl createSocketImpl() {
                return null;
            }
        };
        boolean thrown = false;
        try {
            ServerSocket.setSocketFactory(anotherFactory);
        } catch (SocketException se) {
            thrown = true;
            String msg = se.getMessage();
            if (msg == null || !msg.contains("factory already defined")) {
                throw new AssertionError("SocketException message unexpected: " + msg);
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected SocketException not thrown on second setSocketFactory call");
        }
    }
}
