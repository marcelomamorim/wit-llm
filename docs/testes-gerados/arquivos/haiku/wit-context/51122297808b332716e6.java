/*
 * @test
 * @summary Checks public SSLEngine.setEnabledCipherSuites behavior for null and unknown suite names, and round-trip of a supported suite
 * @run main SSLEngineImplWitupTest
 */

import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public class SSLEngineImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, null, null);
        SSLEngine engine = ctx.createSSLEngine();

        boolean nullRejected = false;
        try {
            engine.setEnabledCipherSuites(null);
        } catch (IllegalArgumentException expected) {
            nullRejected = true;
        }
        if (!nullRejected) {
            throw new RuntimeException("Expected IllegalArgumentException for null suites");
        }

        String[] supported = engine.getSupportedCipherSuites();
        if (supported == null || supported.length == 0) {
            throw new RuntimeException("No supported cipher suites reported");
        }
        String pick = supported[0];
        engine.setEnabledCipherSuites(new String[]{pick});
        String[] enabled = engine.getEnabledCipherSuites();
        if (!Arrays.equals(enabled, new String[]{pick})) {
            throw new RuntimeException("Round-trip mismatch: " + Arrays.toString(enabled));
        }

        boolean unknownRejected = false;
        try {
            engine.setEnabledCipherSuites(new String[]{"TLS_FAKE_UNKNOWN_SUITE"});
        } catch (IllegalArgumentException expected) {
            unknownRejected = true;
        }
        if (!unknownRejected) {
            throw new RuntimeException("Expected IllegalArgumentException for unknown suite");
        }

        String[] afterFailure = engine.getEnabledCipherSuites();
        if (!Arrays.equals(afterFailure, new String[]{pick})) {
            throw new RuntimeException("Enabled suites changed after rejected input: "
                    + Arrays.toString(afterFailure));
        }
    }
}
