/*
 * @test
 * @summary Test CRLNumberExtension constructor with BigInteger argument
 * @run main CRLNumberExtensionWitupTest
 */

import sun.security.x509.CRLNumberExtension;
import sun.security.x509.PKIXExtensions;
import java.math.BigInteger;
import java.io.IOException;

public class CRLNumberExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBigInteger();
        testNullBigInteger();
    }

    private static void testValidBigInteger() throws IOException {
        BigInteger val = BigInteger.valueOf(12345L);
        CRLNumberExtension ext = new CRLNumberExtension(val);
        if (!val.equals(ext.get(CRLNumberExtension.NUMBER))) {
            throw new RuntimeException("CRLNumberExtension did not store the correct BigInteger value");
        }
        if (!PKIXExtensions.CRLNumber_Id.equals(ext.getExtensionId())) {
            throw new RuntimeException("ExtensionId mismatch");
        }
        if (ext.isCritical()) {
            throw new RuntimeException("Extension should not be critical by default");
        }
    }

    private static void testNullBigInteger() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension((BigInteger) null);
        Object val = ext.get(CRLNumberExtension.NUMBER);
        if (val != null) {
            throw new RuntimeException("CRLNumberExtension with null BigInteger should have null value");
        }
        if (ext.getExtensionValue() != null) {
            throw new RuntimeException("Extension value should be null when crlNumber is null");
        }
    }
}
