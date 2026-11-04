/*
 * @test
 * @summary Verifies the public no-arg constructor of SSLServerSocketFactoryImpl builds a usable factory
 * @modules java.base/sun.security.ssl
 * @run main SSLServerSocketFactoryImplWitupTest
 */

import javax.net.ssl.SSLServerSocketFactory;
import sun.security.ssl.SSLServerSocketFactoryImpl;

public class SSLServerSocketFactoryImplWitupTest {
    public static void main(String[] args) throws Exception {
        SSLServerSocketFactoryImpl factory = new SSLServerSocketFactoryImpl();
        if (factory == null) {
            throw new RuntimeException("Constructor returned null");
        }
        if (!(factory instanceof SSLServerSocketFactory)) {
            throw new RuntimeException("Factory is not an SSLServerSocketFactory");
        }

        String[] suites = factory.getDefaultCipherSuites();
        if (suites == null || suites.length == 0) {
            throw new RuntimeException("No default cipher suites exposed by factory");
        }
        for (String suite : suites) {
            if (suite == null || suite.isEmpty()) {
                throw new RuntimeException("Invalid cipher suite name returned");
            }
        }

        SSLServerSocketFactoryImpl second = new SSLServerSocketFactoryImpl();
        String[] secondSuites = second.getDefaultCipherSuites();
        if (secondSuites == null || secondSuites.length != suites.length) {
            throw new RuntimeException("Factories created by constructor report inconsistent default suites");
        }
    }
}
