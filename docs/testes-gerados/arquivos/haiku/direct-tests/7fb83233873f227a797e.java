/*
 * @test
 * @summary Verifies that the public no-arg SSLSocketFactoryImpl constructor yields a usable SSLSocketFactory whose createSocket() returns an unconnected SSLSocket
 * @modules java.base/sun.security.ssl
 * @run main SSLSocketFactoryImplWitupTest
 */

import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import sun.security.ssl.SSLSocketFactoryImpl;

public class SSLSocketFactoryImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSocketFactoryImpl factory = new SSLSocketFactoryImpl();

        if (factory == null) {
            throw new RuntimeException("Constructor returned null");
        }

        if (!(factory instanceof SSLSocketFactory)) {
            throw new RuntimeException("Factory is not an SSLSocketFactory");
        }

        Socket socket = factory.createSocket();
        try {
            if (!(socket instanceof SSLSocket)) {
                throw new RuntimeException("createSocket() did not return an SSLSocket: "
                        + socket.getClass().getName());
            }
            if (socket.isConnected()) {
                throw new RuntimeException("createSocket() returned a connected socket");
            }
        } finally {
            socket.close();
        }
    }
}
