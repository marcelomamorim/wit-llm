/*
 * @test
 * @summary Test sun.security.ssl.SSLSocketFactoryImpl.createSocket() returns non-null SSLSocketImpl
 * @run main SSLSocketFactoryImplWitupTest
 */

import sun.security.ssl.SSLSocketFactoryImpl;
import sun.security.ssl.SSLSocketImpl;
import javax.net.ssl.SSLSocketFactory;
import java.net.Socket;

public class SSLSocketFactoryImplWitupTest {
    public static void main(String[] args) throws Exception {
        SSLSocketFactoryImpl factory = new SSLSocketFactoryImpl();
        Socket socket = factory.createSocket();
        if (socket == null) {
            throw new RuntimeException("createSocket() returned null");
        }
        if (!(socket instanceof SSLSocketImpl)) {
            throw new RuntimeException("createSocket() did not return an instance of SSLSocketImpl");
        }
        // Additional check: socket should not be connected initially
        if (socket.isConnected()) {
            throw new RuntimeException("Socket returned by createSocket() should not be connected initially");
        }
        System.out.println("Test passed: createSocket() returns a non-null, unconnected SSLSocketImpl instance.");
    }
}
