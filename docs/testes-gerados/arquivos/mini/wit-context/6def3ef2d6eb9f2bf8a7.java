/*
 * @test
 * @summary Test sun.security.x509.CRLNumberExtension.set(String,Object) method
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;
import sun.security.x509.PKIXExtensions;

public class CRLNumberExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidNameAndBigInteger();
        testSetWithValidNameAndInvalidType();
        testSetWithInvalidName();
    }

    private static void testSetWithValidNameAndBigInteger() throws Exception {
        // Create instance with initial value
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(123));
        // Set with valid name "value" and BigInteger object
        BigInteger newValue = BigInteger.valueOf(456);
        ext.set("value", newValue);
        // No exception expected, internal crlNumber should be updated
        // We cannot access crlNumber directly, but no exception means success
    }

    private static void testSetWithValidNameAndInvalidType() {
        try {
            CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(1));
            // Attempt to set with valid name but invalid type (String)
            ext.set("value", "not a BigInteger");
            throw new RuntimeException("Expected IOException not thrown for invalid type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute must be of type BigInteger")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
            // Expected exception caught
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testSetWithInvalidName() {
        try {
            CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(1));
            // Attempt to set with invalid attribute name
            ext.set("invalidName", BigInteger.valueOf(10));
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
            // Expected exception caught
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }
}
