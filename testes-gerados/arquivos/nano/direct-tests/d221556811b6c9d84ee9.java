/* @test    @summary Test OCSPNonceExtension constructor with valid length    @run main OCSPNonceExtensionWitupTest */

import sun.security.provider.certpath.OCSPNonceExtension;
import java.io.IOException;

public class OCSPNonceExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidLength();
        testConstructorWithZeroLength();
        testConstructorWithNegativeLength();
    }

    static void testConstructorWithValidLength() {
        int length = 16;
        try {
            OCSPNonceExtension ext = new OCSPNonceExtension(length);
            if (ext == null) {
                throw new AssertionError("Constructor returned null");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Unexpected IllegalArgumentException: " + e.getMessage(), e);
        }
    }

    static void testConstructorWithZeroLength() {
        int length = 0;
        try {
            new OCSPNonceExtension(length);
            throw new AssertionError("Expected IllegalArgumentException for zero length");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage(), e);
        }
    }

    static void testConstructorWithNegativeLength() {
        int length = -5;
        try {
            new OCSPNonceExtension(length);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage(), e);
        }
    }
}
