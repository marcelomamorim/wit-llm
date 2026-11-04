/*
 * @test
 * @summary Witup-generated checks for InhibitAnyPolicyExtension.get(String)
 * @modules java.base/sun.security.x509
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(5);
        check(ext.get("skip_certs") == 5, "lowercase name must return skipCerts");
        check(ext.get("SKIP_CERTS") == 5, "name lookup must be case-insensitive");
        check(ext.get("Skip_Certs") == 5, "mixed-case name must be accepted");

        InhibitAnyPolicyExtension zero = new InhibitAnyPolicyExtension(0);
        check(zero.get("skip_certs") == 0, "zero skipCerts must be returned");

        expectIOException(() -> ext.get("unknown"), "unrecognized name must throw IOException");
        expectIOException(() -> ext.get(""), "empty name must throw IOException");
        expectIOException(() -> ext.get("skip"), "partial name must throw IOException");

        expectIOException(() -> new InhibitAnyPolicyExtension(-2), "skipCerts below -1 must be rejected");
    }

    private interface IOAction {
        Object run() throws IOException;
    }

    private static void expectIOException(IOAction action, String message) {
        try {
            action.run();
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException(message);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
