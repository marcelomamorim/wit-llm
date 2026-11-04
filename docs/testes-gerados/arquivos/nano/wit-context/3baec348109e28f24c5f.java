/* @test    @summary Test createServerSocket method for SSLServerSocketFactory with expected exception path    @run main SSLServerSocketFactoryWitupTest */

import java.io.IOException;
import java.net.SocketException;
import javax.net.ssl.SSLServerSocketFactory;

public class SSLServerSocketFactoryWitupTest {

    public static void main(String[] args) {
        testCreateServerSocketThrowsSocketException();
    }

    public static void testCreateServerSocketThrowsSocketException() {
        SSLServerSocketFactory factory = new SSLServerSocketFactory() {
            @Override
            public java.net.ServerSocket createServerSocket() throws IOException {
                throw new SocketException("Simulated socket exception");
            }
        };
        try {
            factory.createServerSocket();
        } catch (IOException e) {
            if (e instanceof SocketException) {
                System.out.println("Caught expected SocketException: " + e.getMessage());
            } else {
                throw e;
            }
        }
    }
}
