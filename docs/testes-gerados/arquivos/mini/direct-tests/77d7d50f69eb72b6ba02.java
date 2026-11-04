/*
 * @test
 * @summary Test createServerSocket() throws SocketException as expected
 * @run main javax.net.ServerSocketFactoryWitupTest
 */

import javax.net.ServerSocketFactory;
import java.net.SocketException;
import java.io.IOException;

public class ServerSocketFactoryWitupTest {
    public static void main(String[] args) throws Exception {
        ServerSocketFactory factory = new ServerSocketFactory() {
            // anonymous subclass to allow instantiation
        };

        try {
            factory.createServerSocket();
            throw new RuntimeException("Expected SocketException not thrown");
        } catch (SocketException se) {
            String msg = se.getMessage();
            if (msg == null || !msg.contains("Unbound server sockets not implemented")) {
                throw new RuntimeException("SocketException message unexpected: " + msg);
            }
        }
    }
}
