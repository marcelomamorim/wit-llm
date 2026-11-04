/*
 * @test
 * @summary Test setEnabledCipherSuites method of SSLServerSocketImpl for valid and invalid inputs
 * @run main SSLServerSocketImplWitupTest
 */

import sun.security.ssl.SSLServerSocketImpl;
import sun.security.ssl.SSLContextImpl;
import sun.security.ssl.CipherSuite;

import java.io.IOException;

public class SSLServerSocketImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a minimal SSLContextImpl stub for constructor
        SSLContextImpl sslContext = new SSLContextImplStub();

        SSLServerSocketImpl serverSocket = new SSLServerSocketImpl(sslContext);

        // Test 1: setEnabledCipherSuites with valid cipher suites
        String[] validSuites = new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA"};
        serverSocket.setEnabledCipherSuites(validSuites);
        String[] enabledSuites = serverSocket.getEnabledCipherSuites();
        if (enabledSuites == null || enabledSuites.length != validSuites.length) {
            throw new RuntimeException("Enabled cipher suites length mismatch");
        }
        for (int i = 0; i < validSuites.length; i++) {
            if (!validSuites[i].equals(enabledSuites[i])) {
                throw new RuntimeException("Enabled cipher suite mismatch at index " + i);
            }
        }

        // Test 2: setEnabledCipherSuites with empty array
        serverSocket.setEnabledCipherSuites(new String[0]);
        enabledSuites = serverSocket.getEnabledCipherSuites();
        if (enabledSuites == null || enabledSuites.length != 0) {
            throw new RuntimeException("Enabled cipher suites should be empty array");
        }

        // Test 3: setEnabledCipherSuites with null - expect IllegalArgumentException
        boolean threw = false;
        try {
            serverSocket.setEnabledCipherSuites(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException when passing null to setEnabledCipherSuites");
        }

        System.out.println("All tests passed.");
    }

    // Minimal stub for SSLContextImpl to allow SSLServerSocketImpl construction
    static class SSLContextImplStub extends SSLContextImpl {
        SSLContextImplStub() throws IOException {
            super(null, null, null);
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA"};
        }
    }
}
