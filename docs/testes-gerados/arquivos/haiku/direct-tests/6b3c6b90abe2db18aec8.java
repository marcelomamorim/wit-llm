/*
 * @test
 * @summary Verify CRLNumberExtension.delete accepts the NUMBER attribute name case-insensitively and rejects unknown names with IOException
 * @modules java.base/sun.security.x509
 * @run main CRLNumberExtensionWitupTest
 */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteNumberNameIsIdempotent();
        testDeleteIsCaseInsensitive();
        testDeleteUnknownNameThrows();
        testUnknownNameStillRejectedAfterDelete();
    }

    static void testDeleteNumberNameIsIdempotent() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(7));
        ext.delete(CRLNumberExtension.NUMBER);
        ext.delete(CRLNumberExtension.NUMBER);
    }

    static void testDeleteIsCaseInsensitive() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.valueOf(11));
        ext.delete("VALUE");
        ext.delete("Value");
    }

    static void testDeleteUnknownNameThrows() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(5);
        try {
            ext.delete("bogus");
            throw new RuntimeException("Expected IOException for unknown attribute name");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testUnknownNameStillRejectedAfterDelete() throws IOException {
        CRLNumberExtension ext = new CRLNumberExtension(BigInteger.TEN);
        ext.delete(CRLNumberExtension.NUMBER);
        try {
            ext.delete("unknown");
            throw new RuntimeException("Expected IOException for unknown attribute name after delete");
        } catch (IOException expected) {
            // expected
        }
    }
}
