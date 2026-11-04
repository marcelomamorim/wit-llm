/*
 * @test
 * @summary Verifies that the public SSLServerSocketFactoryImpl constructor yields a usable SSLServerSocketFactory with non-empty default cipher suites
 * @modules java.base/sun.security.ssl
 * @run main SSLServerSocketFactoryImplWitupTest
 */

import javax.net.ssl.SSLServerSocketFactory;
import sun.security.ssl.SSLServerSocketFactoryImpl;

public class SSLServerSocketFactoryImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLServerSocketFactoryImpl factory = new SSLServerSocketFactoryImpl();
        if (factory == null) {
            throw new RuntimeException("constructor returned null");
        }
        if (!(factory instanceof SSLServerSocketFactory)) {
            throw new RuntimeException("factory is not an SSLServerSocketFactory");
        }

        String[] suites = factory.getDefaultCipherSuites();
        if (suites == null || suites.length == 0) {
            throw new RuntimeException("default cipher suites are null or empty");
        }
        for (String suite : suites) {
            if (suite == null || suite.isEmpty()) {
                throw new RuntimeException("null or empty cipher suite name");
            }
        }

        SSLServerSocketFactoryImpl second = new SSLServerSocketFactoryImpl();
        String[] secondSuites = second.getDefaultCipherSuites();
        if (secondSuites == null || secondSuites.length == 0) {
            throw new RuntimeException("second factory has no default cipher suites");
        }
    }
}
