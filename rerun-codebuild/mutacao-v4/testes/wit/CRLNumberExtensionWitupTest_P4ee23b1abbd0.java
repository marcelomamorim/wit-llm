/* @test
   @summary Testa o método get(String name) de CRLNumberExtension para comportamento esperado.
   @run main CRLNumberExtensionWitupTest_P4ee23b1abbd0
 */
import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;
public class CRLNumberExtensionWitupTest_P4ee23b1abbd0 {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }
    private static void testGetWithValidName() throws Exception {
        BigInteger crlNum = BigInteger.valueOf(12345);
        CRLNumberExtension ext = new CRLNumberExtension(crlNum);
        BigInteger result = ext.get("value");
        if (result == null || !result.equals(crlNum)) {
            throw new AssertionError("Expected crlNumber to be " + crlNum + " but got " + result);
        }
    }
    private static void testGetWithInvalidName() {
        BigInteger crlNum;
        try {
            crlNum = BigInteger.valueOf(12345);
            CRLNumberExtension ext = new CRLNumberExtension(crlNum);
            ext.get("invalid");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
