/*
 * @test
 * @summary Test sun.security.x509.CRLNumberExtension constructor with BigInteger
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;
import sun.security.x509.PKIXExtensions;

public class CRLNumberExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBigInteger();
        testNullBigInteger();
    }

    private static void testValidBigInteger() throws IOException {
        BigInteger val = BigInteger.valueOf(123456789L);
        CRLNumberExtension ext = new CRLNumberExtension(val);
        if (ext == null) {
            throw new RuntimeException("CRLNumberExtension constructor returned null");
        }
        // Check that the extensionValue is not null after construction
        byte[] encoded = ext.getExtensionValue();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded extension value is null or empty");
        }
    }

    private static void testNullBigInteger() {
        try {
            new CRLNumberExtension((BigInteger) null);
            throw new RuntimeException("Expected IOException not thrown for null BigInteger");
        } catch (IOException e) {
            // Expected: encoding null crlNumber should cause IOException or handled gracefully
        } catch (NullPointerException e) {
            // Acceptable if NPE is thrown instead of IOException
        }
    }
}
