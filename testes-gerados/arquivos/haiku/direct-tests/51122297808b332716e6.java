/*
 * @test
 * @summary Verifies that setEnabledCipherSuites on the default SSLEngine is observable through getEnabledCipherSuites
 * @run main SSLEngineImplWitupTest
 */

import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public class SSLEngineImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLContext ctx = SSLContext.getDefault();
        String[] supported = ctx.getSupportedCipherSuites();
        if (supported.length == 0) {
            throw new RuntimeException("No supported cipher suites");
        }

        testSingleSuite(ctx, supported[0]);
        if (supported.length >= 2) {
            testTwoSuites(ctx, supported[0], supported[1]);
        }
        testDoesNotChangeSupported(ctx, supported);
    }

    private static void testSingleSuite(SSLContext ctx, String suite) {
        SSLEngine engine = ctx.createSSLEngine();
        engine.setEnabledCipherSuites(new String[] { suite });
        String[] enabled = engine.getEnabledCipherSuites();
        if (!Arrays.equals(enabled, new String[] { suite })) {
            throw new RuntimeException("Expected [" + suite + "] but got "
                    + Arrays.toString(enabled));
        }
    }

    private static void testTwoSuites(SSLContext ctx, String s1, String s2) {
        SSLEngine engine = ctx.createSSLEngine();
        engine.setEnabledCipherSuites(new String[] { s1, s2 });
        String[] enabled = engine.getEnabledCipherSuites();
        if (!Arrays.equals(enabled, new String[] { s1, s2 })) {
            throw new RuntimeException("Expected [" + s1 + ", " + s2 + "] but got "
                    + Arrays.toString(enabled));
        }
    }

    private static void testDoesNotChangeSupported(SSLContext ctx, String[] before) {
        SSLEngine engine = ctx.createSSLEngine();
        engine.setEnabledCipherSuites(new String[] { before[0] });
        String[] after = ctx.getSupportedCipherSuites();
        if (!Arrays.equals(before, after)) {
            throw new RuntimeException("Supported cipher suites changed after engine configuration");
        }
    }
}
