/*
 * @test
 * @summary Test sun.security.ssl.SSLSocketFactoryImpl constructor behavior
 * @run main SSLSocketFactoryImplWitupTest
 */

import sun.security.ssl.SSLSocketFactoryImpl;

public class SSLSocketFactoryImplWitupTest {
    public static void main(String[] args) throws Exception {
        // The constructor throws Exception if DefaultSSLContextHolder.reservedException != null
        // We cannot set or clear reservedException here, so we test normal construction
        SSLSocketFactoryImpl factory = new SSLSocketFactoryImpl();
        if (factory == null) {
            throw new AssertionError("SSLSocketFactoryImpl constructor returned null");
        }
    }
}
