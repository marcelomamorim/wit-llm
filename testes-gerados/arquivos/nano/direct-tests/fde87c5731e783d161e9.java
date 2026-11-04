/*
 * @test
 * @summary Test default constructor of SSLServerSocketFactoryImpl
 * @run main SSLServerSocketFactoryImplWitupTest
 */

import java.net.ServerSocket;
import java.io.IOException;
import sun.security.ssl.SSLServerSocketFactoryImpl;

public class SSLServerSocketFactoryImplWitupTest {

    public static void main(String[] args) throws Exception {
        testDefaultConstructorCreatesInstance();
    }

    public static void testDefaultConstructorCreatesInstance() throws IOException {
        SSLServerSocketFactoryImpl factory = new SSLServerSocketFactoryImpl();
        ServerSocket serverSocket = factory.createServerSocket();
        if (serverSocket == null) {
            throw new AssertionError("ServerSocket should not be null");
        }
        serverSocket.close();
    }
}
