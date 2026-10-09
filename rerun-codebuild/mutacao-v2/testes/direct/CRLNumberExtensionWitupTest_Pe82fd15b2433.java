/*
 * @test
 * @summary Test sun.security.x509.CRLNumberExtension.get(String) method
 * @run main CRLNumberExtensionWitupTest_Pe82fd15b2433
 */

import sun.security.x509.CRLNumberExtension;
import sun.security.x509.PKIXExtensions;
import java.math.BigInteger;
import java.io.IOException;

public class CRLNumberExtensionWitupTest_Pe82fd15b2433 {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        // Create CRLNumberExtension with known BigInteger value
        BigInteger expected = BigInteger.valueOf(12345L);
        CRLNumberExtension ext = new CRLNumberExtension(expected);

        // get(NUMBER) should return the same BigInteger
        BigInteger actual = ext.get("value");
        if (!expected.equals(actual)) {
            throw new RuntimeException("get(\"value\") returned wrong BigInteger: expected "
                + expected + ", got " + actual);
        }

        // Also test case-insensitivity
        actual = ext.get("VaLuE");
        if (!expected.equals(actual)) {
            throw new RuntimeException("get(\"VaLuE\") returned wrong BigInteger: expected "
                + expected + ", got " + actual);
        }
    }

    private static void testGetWithInvalidName() {
        try {
            CRLNumberExtension ext = new CRLNumberExtension(1);
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException for invalid attribute name not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
            // Expected exception, test passed
        }
    }
}
