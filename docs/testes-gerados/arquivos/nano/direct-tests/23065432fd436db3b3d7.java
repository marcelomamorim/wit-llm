/* @test    @summary Test createSocket() method of SSLSocketFactoryImpl    @run main SSLSocketFactoryImplWitupTest */

import java.io.*;
import java.net.*;
import sun.security.ssl.SSLSocketFactoryImpl;

public class SSLSocketFactoryImplWitupTest {

    public static void main(String[] args) throws Exception {
        testCreateSocketReturnsSSLSocketImpl();
    }

    public static void testCreateSocketReturnsSSLSocketImpl() {
        SSLSocketFactoryImpl factory = new SSLSocketFactoryImpl();
        Socket socket = factory.createSocket();
        if (!(socket instanceof SSLSocketImpl)) {
            throw new AssertionError("Expected instance of SSLSocketImpl, but got: " + socket.getClass());
        }
    }
}
