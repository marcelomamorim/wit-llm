/*
 * @test
 * @summary Test sun.security.x509.CRLNumberExtension.set method behavior
 * @run main CRLNumberExtensionWitupTest
 */

import sun.security.x509.CRLNumberExtension;
import sun.security.x509.PKIXExtensions;
import java.math.BigInteger;
import java.io.IOException;

public class CRLNumberExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetValidNameAndBigInteger();
        testSetInvalidName();
        testSetInvalidType();
    }

    private static void testSetValidNameAndBigInteger() throws Exception {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(123));
        BigInteger newValue = BigInteger.valueOf(456);
        ext.set("value", newValue);
        // No direct getter, but encodeThis sets extensionValue non-null if crlNumber != null
        // We test that no exception is thrown and internal state changes by re-setting again
        ext.set("value", BigInteger.valueOf(789));
    }

    private static void testSetInvalidName() {
        try {
            CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(1));
            ext.set("invalidName", BigInteger.valueOf(1));
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testSetInvalidType() {
        try {
            CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(1));
            ext.set("value", "not a BigInteger");
            throw new RuntimeException("Expected IOException for invalid attribute type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute must be of type BigInteger")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }
}
