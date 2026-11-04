/* @test    @summary Test the get method of CRLNumberExtension for valid and invalid attribute names    @run main CRLNumberExtensionWitupTest */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        BigInteger crlNum = BigInteger.valueOf(12345);
        CRLNumberExtension extension = new CRLNumberExtension(crlNum);
        BigInteger result = extension.get("value");
        if (!crlNum.equals(result)) {
            throw new AssertionError("Expected: " + crlNum + ", but got: " + result);
        }
    }

    private static void testGetWithInvalidName() {
        BigInteger crlNum = BigInteger.valueOf(67890);
        try {
            CRLNumberExtension extension = new CRLNumberExtension(crlNum);
            extension.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
