/*
 * @test
 * @summary Test createSocket() method of SSLSocketFactoryImpl for expected behavior.
 * @run main SSLSocketFactoryImplWitupTest
 */

import java.io.*;
import java.net.*;
import sun.security.ssl.SSLSocketFactoryImpl;

public class SSLSocketFactoryImplWitupTest {

    public static void main(String[] args) throws Exception {
        testCreateSocketReturnsSSLSocketImpl();
    }

    private static void testCreateSocketReturnsSSLSocketImpl() {
        SSLSocketFactoryImpl factory = new SSLSocketFactoryImpl();
        Socket socket = factory.createSocket();
        if (socket == null) {
            throw new AssertionError("createSocket() returned null");
        }
        if (!(socket instanceof sun.security.ssl.SSLSocketImpl)) {
            throw new AssertionError("createSocket() did not return SSLSocketImpl instance");
        }
    }
}
