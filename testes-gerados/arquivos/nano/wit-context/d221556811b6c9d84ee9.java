/* @test    @summary Test for OCSPNonceExtension constructor with positive length    @run main OCSPNonceExtensionWitupTest */

import sun.security.provider.certpath.OCSPNonceExtension;
import java.io.IOException;

public class OCSPNonceExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidLength();
        testConstructorWithZeroLength();
        testConstructorWithNegativeLength();
    }

    private static void testConstructorWithValidLength() throws IOException {
        int length = 10;
        OCSPNonceExtension ext = new OCSPNonceExtension(length);
        assert ext != null : "Extension should not be null";
    }

    private static void testConstructorWithZeroLength() {
        try {
            new OCSPNonceExtension(0);
            assert false : "Expected IllegalArgumentException for zero length";
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            assert false : "Unexpected exception: " + e;
        }
    }

    private static void testConstructorWithNegativeLength() {
        try {
            new OCSPNonceExtension(-5);
            assert false : "Expected IllegalArgumentException for negative length";
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            assert false : "Unexpected exception: " + e;
        }
    }
}
