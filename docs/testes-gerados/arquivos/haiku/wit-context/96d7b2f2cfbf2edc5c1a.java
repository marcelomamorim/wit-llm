/*
 * @test
 * @summary Verify setEnabledCipherSuites rejects null and accepts a supported suite through the public SSLServerSocket API
 * @run main SSLServerSocketImplWitupTest
 */

import java.io.IOException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;

public class SSLServerSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLContext ctx = SSLContext.getDefault();
        try (SSLServerSocket ss = (SSLServerSocket) ctx.getServerSocketFactory().createServerSocket()) {
            testNullSuitesRejected(ss);
            testValidSuiteRoundTrip(ss);
        }
    }

    private static void testNullSuitesRejected(SSLServerSocket ss) {
        boolean thrown = false;
        try {
            ss.setEnabledCipherSuites(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for null suites");
        }
    }

    private static void testValidSuiteRoundTrip(SSLServerSocket ss) throws IOException {
        String[] supported = ss.getSupportedCipherSuites();
        if (supported == null || supported.length == 0) {
            throw new RuntimeException("No supported cipher suites available");
        }
        String expected = supported[0];
        ss.setEnabledCipherSuites(new String[] { expected });
        String[] enabled = ss.getEnabledCipherSuites();
        if (enabled == null || enabled.length != 1) {
            throw new RuntimeException("Expected exactly one enabled suite");
        }
        if (!expected.equals(enabled[0])) {
            throw new RuntimeException("Enabled suite mismatch: " + enabled[0]);
        }
    }
}
