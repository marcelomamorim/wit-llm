/*
 * @test
 * @summary Verify OIDName(DerValue) decodes OID values and rejects non-OID DER tags with IOException
 * @modules java.base/sun.security.util java.base/sun.security.x509
 * @run main OIDNameWitupTest
 */

import java.io.IOException;

import sun.security.util.DerValue;
import sun.security.x509.OIDName;

public class OIDNameWitupTest {

    public static void main(String[] args) throws Exception {
        testValidOidDerValue();
        testNonOidTagThrowsIOException();
        System.out.println("PASS");
    }

    static void testValidOidDerValue() throws Exception {
        byte[] encoding = {0x06, 0x02, 0x2A, 0x03};
        DerValue dv = new DerValue(encoding);
        OIDName name = new OIDName(dv);
        String actual = name.getOID().toString();
        if (!"1.2.3".equals(actual)) {
            throw new RuntimeException("Expected OID 1.2.3 but got " + actual);
        }
    }

    static void testNonOidTagThrowsIOException() throws Exception {
        byte[] encoding = {0x02, 0x01, 0x05};
        DerValue dv = new DerValue(encoding);
        boolean thrown = false;
        try {
            new OIDName(dv);
        } catch (IOException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for non-OID DER tag");
        }
    }
}
