/*
 * @test
 * @summary Test sun.security.ssl.SSLSocketFactoryImpl createSocket() method
 * @run main SSLSocketFactoryImplWitupTest
 */

import java.net.Socket;
import sun.security.ssl.SSLSocketFactoryImpl;

public class SSLSocketFactoryImplWitupTest {
    public static void main(String[] args) throws Exception {
        SSLSocketFactoryImpl factory = new SSLSocketFactoryImpl();
        Socket socket = factory.createSocket();
        if (socket == null) {
            throw new RuntimeException("createSocket() returned null");
        }
        // Check that the returned socket is connected or not (should be unconnected)
        if (socket.isConnected()) {
            throw new RuntimeException("createSocket() returned a connected socket");
        }
        // Check that the socket is an instance of Socket (should be true)
        if (!(socket instanceof Socket)) {
            throw new RuntimeException("createSocket() did not return a Socket instance");
        }
        System.out.println("Test passed: createSocket() returned a valid unconnected Socket instance.");
    }
}
