/*
 * @test
 * @summary Test setEnabledCipherSuites method of SSLServerSocketImpl
 * @run main SSLServerSocketImplWitupTest
 */

import sun.security.ssl.SSLServerSocketImpl;
import sun.security.ssl.SSLContextImpl;
import sun.security.ssl.CipherSuite;

public class SSLServerSocketImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy SSLContextImpl instance for constructor
        SSLContextImpl sslContext = new SSLContextImpl();

        SSLServerSocketImpl serverSocket = new SSLServerSocketImpl(sslContext);

        // Test with null input - should set enabledCipherSuites to empty array
        serverSocket.setEnabledCipherSuites(null);
        String[] enabledSuites = serverSocket.getEnabledCipherSuites();
        if (enabledSuites == null || enabledSuites.length != 0) {
            throw new RuntimeException("Expected empty enabledCipherSuites for null input");
        }

        // Test with empty array input
        serverSocket.setEnabledCipherSuites(new String[0]);
        enabledSuites = serverSocket.getEnabledCipherSuites();
        if (enabledSuites == null || enabledSuites.length != 0) {
            throw new RuntimeException("Expected empty enabledCipherSuites for empty input");
        }

        // Test with valid cipher suite names
        String[] validSuites = new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA"};
        serverSocket.setEnabledCipherSuites(validSuites);
        enabledSuites = serverSocket.getEnabledCipherSuites();
        if (enabledSuites.length != validSuites.length) {
            throw new RuntimeException("Mismatch in enabled cipher suites length");
        }
        for (int i = 0; i < validSuites.length; i++) {
            if (!enabledSuites[i].equals(validSuites[i])) {
                throw new RuntimeException("Enabled cipher suite mismatch at index " + i);
            }
        }

        // Test with invalid cipher suite names - should filter out invalid ones
        String[] mixedSuites = new String[] {"INVALID_CIPHER", "TLS_RSA_WITH_AES_128_CBC_SHA"};
        serverSocket.setEnabledCipherSuites(mixedSuites);
        enabledSuites = serverSocket.getEnabledCipherSuites();
        if (enabledSuites.length != 1 || !enabledSuites[0].equals("TLS_RSA_WITH_AES_128_CBC_SHA")) {
            throw new RuntimeException("Expected only valid cipher suites to be enabled");
        }
    }
}
