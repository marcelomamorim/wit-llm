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
        SSLContextImpl dummyContext = new DummySSLContextImpl();
        SSLEngineImpl engine = new SSLEngineImpl(dummyContext);

        // Test 1: Passing null should throw IllegalArgumentException
        try {
            engine.setEnabledCipherSuites(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }

        // Test 2: Passing empty array should set enabledCipherSuites to empty list
        String[] emptySuites = new String[0];
        engine.setEnabledCipherSuites(emptySuites);
        // We cannot access conContext.sslConfig.enabledCipherSuites directly,
        // but no exception means success.

        // Test 3: Passing valid cipher suites array
        String[] validSuites = new String[] {"TLS_RSA_WITH_AES_128_CBC_SHA"};
        engine.setEnabledCipherSuites(validSuites);
        // No exception means success

        // Test 4: Passing invalid cipher suite should throw IllegalArgumentException
        String[] invalidSuites = new String[] {"INVALID_CIPHER_SUITE"};
        try {
            engine.setEnabledCipherSuites(invalidSuites);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for invalid cipher suite");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    // Minimal dummy SSLContextImpl to allow SSLEngineImpl construction
    static class DummySSLContextImpl extends SSLContextImpl {
        DummySSLContextImpl() {
            super(null, null, null);
        }

        @Override
        public boolean isDTLS() {
            return false;
        }
    }
}