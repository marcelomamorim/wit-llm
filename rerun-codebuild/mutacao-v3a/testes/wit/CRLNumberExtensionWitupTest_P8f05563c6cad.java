/* @test    @summary Test for sun.security.x509.CRLNumberExtension.get(String) to verify exception handling and normal behavior    @run main CRLNumberExtensionWitupTest_P8f05563c6cad */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest_P8f05563c6cad {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws Exception {
        BigInteger value = BigInteger.valueOf(12345);
        CRLNumberExtension extension = new CRLNumberExtension(value);
        BigInteger result = extension.get("value");
        if (result == null || !result.equals(value)) {
            throw new AssertionError("Expected value: " + value + ", but got: " + result);
        }
    }

    private static void testGetWithInvalidName() {
        BigInteger value = BigInteger.valueOf(54321);
        try {
            CRLNumberExtension extension = new CRLNumberExtension(value);
            extension.get("invalidAttribute");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String expectedMessage = "Attribute name not recognized by" +
                " CertAttrSet:" + extension.extensionName + '.';
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
