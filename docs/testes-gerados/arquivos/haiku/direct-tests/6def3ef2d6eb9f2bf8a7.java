/*
 * @test
 * @summary Verify CRLNumberExtension.set accepts BigInteger values for the "value" attribute and rejects other names or types with IOException
 * @modules java.base/sun.security.x509
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;

import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetBigIntegerValueAccepted();
        testSetIsCaseInsensitiveForValueName();
        testSetNonBigIntegerRejected();
        testSetNullValueRejected();
        testSetUnknownNameRejected();
    }

    static void testSetBigIntegerValueAccepted() throws Exception {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.ONE);
        ext.set(CRLNumberExtension.NUMBER, BigInteger.valueOf(42));
    }

    static void testSetIsCaseInsensitiveForValueName() throws Exception {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.ONE);
        ext.set("VALUE", BigInteger.valueOf(7));
        ext.set("Value", BigInteger.valueOf(8));
    }

    static void testSetNonBigIntegerRejected() throws Exception {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.ONE);
        boolean thrown = false;
        try {
            ext.set(CRLNumberExtension.NUMBER, Integer.valueOf(5));
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for non-BigInteger value");
        }
    }

    static void testSetNullValueRejected() throws Exception {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.ONE);
        boolean thrown = false;
        try {
            ext.set(CRLNumberExtension.NUMBER, null);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for null value");
        }
    }

    static void testSetUnknownNameRejected() throws Exception {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.ONE);
        boolean thrown = false;
        try {
            ext.set("unknown", BigInteger.TEN);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for unrecognized attribute name");
        }
    }
}
