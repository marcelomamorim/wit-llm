/*
 * @test
 * @summary Verify SSLSocketFactoryImpl.createSocket() returns new unconnected SSLSocket instances
 * @modules java.base/sun.security.ssl
 * @run main SSLSocketFactoryImplWitupTest
 */

import java.net.Socket;
import javax.net.ssl.SSLSocket;
import sun.security.ssl.SSLSocketFactoryImpl;

public class SSLSocketFactoryImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSocketFactoryImpl factory = new SSLSocketFactoryImpl();

        Socket s1 = factory.createSocket();
        Socket s2 = factory.createSocket();

        check(s1 != null, "createSocket() returned null");
        check(s2 != null, "second createSocket() returned null");

        try {
            check(s1 instanceof SSLSocket, "returned socket is not an SSLSocket");
            check(!s1.isConnected(), "unconnected socket reports connected");
            check(!s1.isClosed(), "new socket reports closed");
            check(s1 != s2, "createSocket() returned the same instance twice");
        } finally {
            s1.close();
            s2.close();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
