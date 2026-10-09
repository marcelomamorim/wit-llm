/* @test    @summary Test for sun.security.x509.CRLNumberExtension.get(String) method    @run main CRLNumberExtensionWitupTest_P9827d8da02bc */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest_P9827d8da02bc {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws Exception {
        BigInteger crlNum = BigInteger.valueOf(12345);
        CRLNumberExtension ext = new CRLNumberExtension(crlNum);
        BigInteger result = ext.get("value");
        if (result == null) {
            throw new AssertionError("Expected non-null result for valid name");
        }
        if (!result.equals(crlNum)) {
            throw new AssertionError("Result does not match expected crlNumber");
        }
    }

    private static void testGetWithInvalidName() {
        BigInteger crlNum = BigInteger.valueOf(12345);
        try {
            CRLNumberExtension ext = new CRLNumberExtension(crlNum);
            ext.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
