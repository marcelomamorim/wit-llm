/*
 * @test
 * @summary Verify that createSocket() returns a new unconnected SSL socket on each call
 * @modules java.base/sun.security.ssl
 * @run main SSLSocketFactoryImplWitupTest
 */

import java.net.Socket;
import javax.net.ssl.SSLSocket;

public class SSLSocketFactoryImplWitupTest {

    public static void main(String[] args) throws Exception {
        sun.security.ssl.SSLSocketFactoryImpl factory = new sun.security.ssl.SSLSocketFactoryImpl();
        Socket first = factory.createSocket();
        try {
            if (first == null) {
                throw new RuntimeException("createSocket() returned null");
            }
            if (!(first instanceof SSLSocket)) {
                throw new RuntimeException("createSocket() did not return an SSLSocket: " + first.getClass());
            }
            if (first.isConnected()) {
                throw new RuntimeException("Socket returned by createSocket() must be unconnected");
            }
            if (first.isClosed()) {
                throw new RuntimeException("Socket returned by createSocket() must not be closed");
            }
            Socket second = factory.createSocket();
            try {
                if (second == first) {
                    throw new RuntimeException("createSocket() returned the same socket instance twice");
                }
                if (second.isConnected()) {
                    throw new RuntimeException("Second socket must be unconnected");
                }
            } finally {
                second.close();
            }
        } finally {
            first.close();
        }
    }
}
