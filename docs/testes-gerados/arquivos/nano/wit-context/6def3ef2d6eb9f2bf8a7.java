/* @test    @summary Test for sun.security.x509.CRLNumberExtension.set(String, Object) method    @run main CRLNumberExtensionWitupTest */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidBigInteger();
        testSetWithInvalidType();
    }

    private static void testSetWithValidBigInteger() throws Exception {
        BigInteger bigInt = BigInteger.valueOf(12345);
        CRLNumberExtension ext = new CRLNumberExtension(bigInt);
        // Simulate setting attribute with correct name and type
        ext.set("value", BigInteger.valueOf(67890));
        // Verify that crlNumber was updated
        if (!ext.getClass().getDeclaredField("crlNumber").get(null).equals(BigInteger.valueOf(67890))) {
            throw new AssertionError("crlNumber not updated correctly");
        }
    }

    private static void testSetWithInvalidType() {
        try {
            BigInteger bigInt = BigInteger.valueOf(12345);
            CRLNumberExtension ext = new CRLNumberExtension(bigInt);
            ext.set("value", "invalid type");
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("Attribute must be of type BigInteger")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
