/*
 * @test
 * @summary Test createServerSocket method of SSLServerSocketFactory
 * @run main SSLServerSocketFactoryWitupTest
 */

import javax.net.ssl.SSLServerSocketFactory;
import java.net.ServerSocket;
import java.io.IOException;

public class SSLServerSocketFactoryWitupTest {
    public static void main(String[] args) throws Exception {
        SSLServerSocketFactory factory = new SSLServerSocketFactory() {
            @Override
            public ServerSocket createServerSocket() throws IOException {
                return super.createServerSocket();
            }
        };

        try {
            factory.createServerSocket();
            throw new RuntimeException("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception, test passes
        }
    }
}
