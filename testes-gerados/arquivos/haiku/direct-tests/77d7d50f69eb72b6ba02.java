/*
 * @test
 * @summary Verify that the default createServerSocket() throws SocketException with the expected message and that getDefault() returns a stable instance
 * @run main ServerSocketFactoryWitupTest
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import javax.net.ServerSocketFactory;

public class ServerSocketFactoryWitupTest {

    static class TestFactory extends ServerSocketFactory {
    }

    public static void main(String[] args) throws Exception {
        ServerSocketFactory factory = new TestFactory();
        boolean thrown = false;
        try {
            ServerSocket s = factory.createServerSocket();
            throw new RuntimeException("Expected SocketException but got socket: " + s);
        } catch (SocketException e) {
            thrown = true;
            if (!"Unbound server sockets not implemented".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("SocketException was not thrown");
        }

        ServerSocketFactory d1 = ServerSocketFactory.getDefault();
        ServerSocketFactory d2 = ServerSocketFactory.getDefault();
        if (d1 == null) {
            throw new RuntimeException("getDefault() returned null");
        }
        if (d1 != d2) {
            throw new RuntimeException("getDefault() returned different instances");
        }
    }
}
