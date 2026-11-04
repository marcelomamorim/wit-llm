/*
 * @test
 * @summary Verify the base ServerSocketFactory.createServerSocket() throws SocketException and getDefault() returns a stable instance
 * @run main ServerSocketFactoryWitupTest
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import javax.net.ServerSocketFactory;

public class ServerSocketFactoryWitupTest {

    public static void main(String[] args) throws Exception {
        testBaseCreateServerSocketThrowsSocketException();
        testGetDefaultReturnsSameInstance();
    }

    static void testBaseCreateServerSocketThrowsSocketException() throws IOException {
        ServerSocketFactory factory = new ServerSocketFactory() {};
        try {
            ServerSocket s = factory.createServerSocket();
            throw new AssertionError("Expected SocketException but got socket: " + s);
        } catch (SocketException e) {
            String expected = "Unbound server sockets not implemented";
            if (!expected.equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }

    static void testGetDefaultReturnsSameInstance() {
        ServerSocketFactory first = ServerSocketFactory.getDefault();
        ServerSocketFactory second = ServerSocketFactory.getDefault();
        if (first == null) {
            throw new AssertionError("getDefault() returned null");
        }
        if (first != second) {
            throw new AssertionError("getDefault() returned different instances");
        }
    }
}
