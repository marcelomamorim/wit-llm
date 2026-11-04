/*
 * @test
 * @summary Test default constructor of SSLServerSocketFactoryImpl
 * @run main SSLServerSocketFactoryImplWitupTest
 */

import sun.security.ssl.SSLServerSocketFactoryImpl;

public class SSLServerSocketFactoryImplWitupTest {
    public static void main(String[] args) throws Exception {
        SSLServerSocketFactoryImpl factory = new SSLServerSocketFactoryImpl();
        if (factory == null) {
            throw new RuntimeException("Factory instance is null");
        }
    }
}
