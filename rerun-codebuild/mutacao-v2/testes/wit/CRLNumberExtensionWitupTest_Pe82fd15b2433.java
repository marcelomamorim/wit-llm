/*
 * @test
 * @summary Test sun.security.x509.CRLNumberExtension.get(String) method
 * @run main CRLNumberExtensionWitupTest_Pe82fd15b2433
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;
import sun.security.x509.PKIXExtensions;

public class CRLNumberExtensionWitupTest_Pe82fd15b2433 {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws Exception {
        // Create CRLNumberExtension with known value
        BigInteger expected = BigInteger.valueOf(12345L);
        CRLNumberExtension ext = new CRLNumberExtension(expected);

        // get("value") should return the stored BigInteger
        BigInteger actual = ext.get("value");
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    private static void testGetWithInvalidName() {
        try {
            CRLNumberExtension ext = new CRLNumberExtension(1);
            ext.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected IOException message: " + msg);
            }
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
