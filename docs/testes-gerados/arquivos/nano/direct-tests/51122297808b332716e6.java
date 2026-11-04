/*
 * @test
 * @summary Test setEnabledCipherSuites method of SSLEngineImpl for various inputs.
 * @run main SSLEngineImplWitupTest
 */

import sun.security.ssl.SSLEngineImpl;
import java.util.Arrays;

public class SSLEngineImplWitupTest {

    public static void main(String[] args) {
        testSetEnabledCipherSuitesWithValidInput();
        testSetEnabledCipherSuitesWithEmptyArray();
        testSetEnabledCipherSuitesWithNull();
    }

    private static void testSetEnabledCipherSuitesWithValidInput() {
        SSLEngineImpl engine = new SSLEngineImpl(null);
        String[] suites = {"TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384"};
        engine.setEnabledCipherSuites(suites);
        String[] result = engine.conContext.sslConfig.enabledCipherSuites;
        if (!Arrays.equals(suites, result)) {
            throw new AssertionError("Expected cipher suites: " + Arrays.toString(suites) + ", but got: " + Arrays.toString(result));
        }
    }

    private static void testSetEnabledCipherSuitesWithEmptyArray() {
        SSLEngineImpl engine = new SSLEngineImpl(null);
        String[] suites = {};
        engine.setEnabledCipherSuites(suites);
        String[] result = engine.conContext.sslConfig.enabledCipherSuites;
        if (!Arrays.equals(suites, result)) {
            throw new AssertionError("Expected empty cipher suites, but got: " + Arrays.toString(result));
        }
    }

    private static void testSetEnabledCipherSuitesWithNull() {
        SSLEngineImpl engine = new SSLEngineImpl(null);
        try {
            engine.setEnabledCipherSuites(null);
            String[] result = engine.conContext.sslConfig.enabledCipherSuites;
            if (result != null) {
                throw new AssertionError("Expected null cipher suites, but got: " + Arrays.toString(result));
            }
        } catch (NullPointerException e) {
            // Expected if null is not handled
        }
    }
}
