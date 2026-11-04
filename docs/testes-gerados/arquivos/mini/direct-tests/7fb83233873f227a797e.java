/*
 * @test
 * @summary Test default constructor of SSLSocketFactoryImpl
 * @run main SSLSocketFactoryImplWitupTest
 */

import sun.security.ssl.SSLSocketFactoryImpl;

public class SSLSocketFactoryImplWitupTest {
    public static void main(String[] args) throws Exception {
        SSLSocketFactoryImpl factory = new SSLSocketFactoryImpl();
        if (factory == null) {
            throw new RuntimeException("SSLSocketFactoryImpl constructor returned null");
        }
    }
}
