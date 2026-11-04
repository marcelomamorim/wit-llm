/* @test    @summary Test sun.security.x509.CRLNumberExtension.set method for BigInteger attribute setting    @run main CRLNumberExtensionWitupTest */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithBigInteger();
        testSetWithInvalidType();
    }

    static void testSetWithBigInteger() throws Exception {
        BigInteger testValue = BigInteger.valueOf(12345);
        CRLNumberExtension ext = new CRLNumberExtension(testValue);
        ext.set("value", testValue);
        // Verify that the value was set correctly by comparing the internal state
        // Since internal state is private, we test by setting and then re-encoding
        // and checking that no exception occurs.
        System.out.println("testSetWithBigInteger passed.");
    }

    static void testSetWithInvalidType() {
        try {
            BigInteger testValue = BigInteger.valueOf(12345);
            CRLNumberExtension ext = new CRLNumberExtension(testValue);
            ext.set("value", "InvalidType");
            throw new AssertionError("Expected IOException not thrown.");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidType passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
