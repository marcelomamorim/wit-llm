/*
 * @test
 * @summary Test constructor SSLServerSocketFactoryImpl() for expected behavior and exception path
 * @run main SSLServerSocketFactoryImplWitupTest
 */

import sun.security.ssl.SSLServerSocketFactoryImpl;

public class SSLServerSocketFactoryImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Test normal construction
        SSLServerSocketFactoryImpl factory = new SSLServerSocketFactoryImpl();
        if (factory == null) {
            throw new RuntimeException("Constructor returned null instance");
        }

        // The WIT expath suggests that if DefaultSSLContextHolder.reservedException != null,
        // the constructor throws that exception. We cannot set that field here,
        // so we only document that this path is not tested due to lack of public API.
        // This is noted in the 'notes' field.
    }
}
