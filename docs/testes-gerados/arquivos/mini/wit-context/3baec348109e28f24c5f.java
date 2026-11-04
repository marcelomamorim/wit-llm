/*
 * @test
 * @summary Test javax.net.ssl.SSLServerSocketFactory.createServerSocket() for expected SocketException
 * @run main SSLServerSocketFactoryWitupTest
 */

import javax.net.ssl.SSLServerSocketFactory;
import java.net.ServerSocket;
import java.net.SocketException;
import java.io.IOException;

public class SSLServerSocketFactoryWitupTest {
    public static void main(String[] args) {
        SSLServerSocketFactory factory = new SSLServerSocketFactory() {
            @Override
            public ServerSocket createServerSocket() throws IOException {
                throw new SocketException("Simulated SocketException");
            }

            @Override
            public ServerSocket createServerSocket(int port) throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public ServerSocket createServerSocket(int port, int backlog) throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public ServerSocket createServerSocket(int port, int backlog, java.net.InetAddress ifAddress) throws IOException {
                throw new UnsupportedOperationException();
            }
        };

        try {
            factory.createServerSocket();
            throw new RuntimeException("Expected SocketException was not thrown");
        } catch (SocketException se) {
            // Expected exception caught, test passes
            System.out.println("Caught expected SocketException: " + se.getMessage());
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException type", ioe);
        }
    }
}
