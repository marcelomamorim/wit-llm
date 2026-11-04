/*
 * @test
 * @summary Test for sun.security.x509.CRLNumberExtension.get(String)
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws Exception {
        BigInteger crlNum = BigInteger.valueOf(12345);
        CRLNumberExtension ext = new CRLNumberExtension(crlNum);
        BigInteger result = ext.get("value");
        if (!crlNum.equals(result)) {
            throw new AssertionError("Expected: " + crlNum + ", but got: " + result);
        }
    }

    private static void testGetWithInvalidName() {
        BigInteger crlNum;
        try {
            crlNum = BigInteger.valueOf(12345);
            CRLNumberExtension ext = new CRLNumberExtension(crlNum);
            ext.get("invalidAttribute");
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + message, e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
