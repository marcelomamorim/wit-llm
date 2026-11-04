/*
 * @test
 * @summary Verify SSLContext.setDefault rejects null and preserves the previously set default context
 * @run main SSLContextWitupTest
 */

import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;

public class SSLContextWitupTest {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        testNullThrowsNullPointerException();
        testNullDoesNotReplaceExistingDefault();
        testSetDefaultThenGetDefaultReturnsSameInstance();
    }

    static void testNullThrowsNullPointerException() {
        try {
            SSLContext.setDefault(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null context");
    }

    static void testNullDoesNotReplaceExistingDefault() throws NoSuchAlgorithmException {
        SSLContext ctx = SSLContext.getInstance("TLS");
        SSLContext.setDefault(ctx);
        try {
            SSLContext.setDefault(null);
        } catch (NullPointerException e) {
            // expected
        }
        if (SSLContext.getDefault() != ctx) {
            throw new RuntimeException("Default context changed after rejected null");
        }
    }

    static void testSetDefaultThenGetDefaultReturnsSameInstance() throws NoSuchAlgorithmException {
        SSLContext ctx = SSLContext.getInstance("TLS");
        SSLContext.setDefault(ctx);
        if (SSLContext.getDefault() != ctx) {
            throw new RuntimeException("getDefault did not return the instance set by setDefault");
        }
    }
}
