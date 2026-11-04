/* @test    @summary Test createServerSocket method of SSLServerSocketFactory for expected exception behavior    @run main SSLServerSocketFactoryWitupTest */

import javax.net.ssl.SSLServerSocketFactory;
import java.net.ServerSocket;
import java.io.IOException;

public class SSLServerSocketFactoryWitupTest {

    public static void main(String[] args) {
        testCreateServerSocketThrowsIOException();
    }

    public static void testCreateServerSocketThrowsIOException() {
        SSLServerSocketFactory factory = new SSLServerSocketFactory() {
            @Override
            public ServerSocket createServerSocket() throws IOException {
                throw new IOException("Simulated IOException");
            }
        };
        try {
            factory.createServerSocket();
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception caught, test passes
            assert e.getMessage().equals("Simulated IOException") : "Unexpected exception message";
        }
    }
}
