/* @test    @summary Test the get(String name) method of CRLNumberExtension for valid and invalid attribute names    @run main CRLNumberExtensionWitupTest_P9827d8da02bc */

import sun.security.x509.CRLNumberExtension;
import java.io.IOException;
import java.math.BigInteger;

public class CRLNumberExtensionWitupTest_P9827d8da02bc {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        BigInteger crlNum = BigInteger.valueOf(12345);
        CRLNumberExtension ext = new CRLNumberExtension(crlNum);
        BigInteger result = ext.get("value");
        if (result == null) {
            throw new AssertionError("FAIL: get("value") returned null");
        }
        if (!result.equals(crlNum)) {
            throw new AssertionError("FAIL: get("value") returned " + result + ", expected " + crlNum);
        }
    }

    private static void testGetWithInvalidName() {
        BigInteger crlNum = BigInteger.valueOf(12345);
        try {
            CRLNumberExtension ext = new CRLNumberExtension(crlNum);
            ext.get("invalidName");
            throw new AssertionError("FAIL: get("invalidName") did not throw IOException");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("FAIL: get("invalidName") threw unexpected IOException: " + msg);
            }
        }
    }
}
