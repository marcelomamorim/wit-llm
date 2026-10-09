/* @test
   @summary Tests the get(String name) method of CRLNumberExtension.
   @run main CRLNumberExtensionWitupTest_P4ee23b1abbd0
 */
import java.math.BigInteger;
import java.io.IOException;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest_P4ee23b1abbd0 {
    public static void main(String[] args) throws Exception {
        testGetWithValidNumber();
        testGetWithInvalidName();
    }

    private static void testGetWithValidNumber() throws Exception {
        BigInteger crlNum = BigInteger.valueOf(12345);
        CRLNumberExtension ext = new CRLNumberExtension(crlNum);
        BigInteger result = ext.get("value");
        if (!result.equals(crlNum)) {
            throw new AssertionError("Expected " + crlNum + " but got " + result);
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
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
