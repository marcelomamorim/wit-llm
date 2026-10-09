/* @test    @summary Test sun.security.x509.CRLNumberExtension.get(String) method    @run main CRLNumberExtensionWitupTest_P8f05563c6cad */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest_P8f05563c6cad {

    public static void main(String[] args) throws Exception {
        testGetWithValidNumber();
        testGetWithInvalidName();
    }

    private static void testGetWithValidNumber() throws IOException {
        BigInteger crlNum = BigInteger.valueOf(12345);
        CRLNumberExtension ext = new CRLNumberExtension(crlNum);
        BigInteger result = ext.get("value");
        if (!crlNum.equals(result)) {
            throw new AssertionError("Expected: " + crlNum + ", but got: " + result);
        }
    }

    private static void testGetWithInvalidName() {
        BigInteger crlNum = BigInteger.valueOf(67890);
        CRLNumberExtension ext;
        try {
            ext = new CRLNumberExtension(crlNum);
            ext.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
