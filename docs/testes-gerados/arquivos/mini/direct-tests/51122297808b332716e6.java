/*
 * @test
 * @summary Test sun.security.ssl.SSLEngineImpl.setEnabledCipherSuites behavior
 * @run main SSLEngineImplWitupTest
 */

import sun.security.ssl.SSLEngineImpl;
import sun.security.ssl.SSLContextImpl;
import sun.security.ssl.CipherSuite;

public class SSLEngineImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create minimal SSLContextImpl stub for constructor
        SSLContextImpl sslContext = new SSLContextImpl();

        // Create SSLEngineImpl instance
        SSLEngineImpl engine = new SSLEngineImpl(sslContext);

        // Test 1: setEnabledCipherSuites with null input (should clear enabled suites)
        engine.setEnabledCipherSuites(null);
        String[] enabledSuites = engine.conContext.sslConfig.enabledCipherSuites;
        if (enabledSuites != null && enabledSuites.length != 0) {
            throw new RuntimeException("Expected enabledCipherSuites to be null or empty after null input");
        }

        // Test 2: setEnabledCipherSuites with empty array (should clear enabled suites)
        engine.setEnabledCipherSuites(new String[0]);
        enabledSuites = engine.conContext.sslConfig.enabledCipherSuites;
        if (enabledSuites != null && enabledSuites.length != 0) {
            throw new RuntimeException("Expected enabledCipherSuites to be null or empty after empty array input");
        }

        // Test 3: setEnabledCipherSuites with valid cipher suite names
        String[] suites = new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA"};
        engine.setEnabledCipherSuites(suites);
        enabledSuites = engine.conContext.sslConfig.enabledCipherSuites;
        if (enabledSuites == null || enabledSuites.length != 2) {
            throw new RuntimeException("Expected enabledCipherSuites to contain 2 suites");
        }
        if (!enabledSuites[0].equals("TLS_RSA_WITH_AES_128_CBC_SHA") || !enabledSuites[1].equals("TLS_RSA_WITH_AES_256_CBC_SHA")) {
            throw new RuntimeException("Enabled cipher suites do not match input suites");
        }

        // Test 4: setEnabledCipherSuites with invalid cipher suite names (should filter out invalid)
        String[] invalidSuites = new String[] {"INVALID_SUITE", "TLS_RSA_WITH_AES_128_CBC_SHA"};
        engine.setEnabledCipherSuites(invalidSuites);
        enabledSuites = engine.conContext.sslConfig.enabledCipherSuites;
        if (enabledSuites == null || enabledSuites.length != 1) {
            throw new RuntimeException("Expected enabledCipherSuites to contain only valid suites");
        }
        if (!enabledSuites[0].equals("TLS_RSA_WITH_AES_128_CBC_SHA")) {
            throw new RuntimeException("Enabled cipher suites do not filter invalid suites correctly");
        }

        System.out.println("All tests passed.");
    }
}
