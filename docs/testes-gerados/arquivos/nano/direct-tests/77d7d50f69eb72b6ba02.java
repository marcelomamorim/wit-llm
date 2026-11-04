/* @test    @summary Test createServerSocket method of ServerSocketFactory class that throws SocketException.    @run main ServerSocketFactoryWitupTest */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class ServerSocketFactoryWitupTest {

    public static void main(String[] args) {
        testCreateServerSocketThrowsException();
    }

    public static void testCreateServerSocketThrowsException() {
        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        try {
            factory.createServerSocket();
            throw new AssertionError("Expected SocketException was not thrown");
        } catch (SocketException e) {
            if (!"Unbound server sockets not implemented".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e, e);
        }
    }
}
