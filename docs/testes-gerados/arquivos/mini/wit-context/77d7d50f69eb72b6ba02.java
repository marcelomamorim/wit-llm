/*
 * @test
 * @summary Test that ServerSocketFactory.createServerSocket() throws SocketException as expected
 * @run main javax.net.ServerSocketFactoryWitupTest
 */

import javax.net.ServerSocketFactory;
import java.net.ServerSocket;
import java.net.SocketException;
import java.io.IOException;

public class ServerSocketFactoryWitupTest {
    public static void main(String[] args) throws Exception {
        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        try {
            ServerSocket ss = factory.createServerSocket();
            throw new RuntimeException("Expected SocketException was not thrown");
        } catch (SocketException se) {
            // Expected exception, test passes
            System.out.println("Caught expected SocketException: " + se.getMessage());
        }
    }
}
