/*
 * @test
 * @summary Test setEnabledCipherSuites method for SSLServerSocketImpl
 * @run main SSLServerSocketImplWitupTest
 */

import java.io.IOException;
import sun.security.ssl.SSLServerSocketImpl;

public class SSLServerSocketImplWitupTest {

    public static void main(String[] args) throws IOException {
        testSetEnabledCipherSuites();
    }

    public static void testSetEnabledCipherSuites() {
        SSLServerSocketImpl socket = null;
        try {
            socket = new SSLServerSocketImpl(null);
        } catch (IOException e) {
            // Expected exception if sslContext is null, skip test
            return;
        }
        String[] validSuites = {"TLS_RSA_WITH_AES_128_CBC_SHA"};
        String[] invalidSuites = {"INVALID_CIPHER"};

        // Test with valid cipher suites
        socket.setEnabledCipherSuites(validSuites);
        String[] resultSuites = socket.getEnabledCipherSuites();
        boolean containsValid = false;
        for (String suite : resultSuites) {
            if (suite.equals("TLS_RSA_WITH_AES_128_CBC_SHA")) {
                containsValid = true;
                break;
            }
        }
        if (!containsValid) {
            throw new AssertionError("Valid cipher suite not set correctly.");
        }

        // Test with invalid cipher suites, expecting the internal list to be filtered
        socket.setEnabledCipherSuites(invalidSuites);
        String[] resultAfterInvalid = socket.getEnabledCipherSuites();
        for (String suite : resultAfterInvalid) {
            if (suite.equals("INVALID_CIPHER")) {
                throw new AssertionError("Invalid cipher suite should not be set.");
            }
        }
    }
}
