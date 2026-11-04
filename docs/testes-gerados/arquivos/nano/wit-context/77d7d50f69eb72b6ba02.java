/* @test    @summary Test for createServerSocket method in javax.net.ServerSocketFactory    @run main ServerSocketFactoryWitupTest */

import javax.net.ServerSocketFactory;
import java.net.ServerSocket;
import java.io.IOException;

public class ServerSocketFactoryWitupTest {

    public static void main(String[] args) {
        testCreateServerSocketReturnsException();
    }

    private static void testCreateServerSocketReturnsException() {
        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        try {
            ServerSocket ss = factory.createServerSocket();
            // If no exception, test fails
            System.err.println("Expected SocketException was not thrown.");
            System.exit(1);
        } catch (IOException e) {
            if (e instanceof java.net.SocketException) {
                // Expected exception
                System.out.println("createServerSocket() threw expected SocketException.");
                System.exit(0);
            } else {
                System.err.println("Unexpected IOException: " + e);
                System.exit(1);
            }
        } catch (Throwable t) {
            System.err.println("Unexpected throwable: " + t);
            System.exit(1);
        }
    }
}
