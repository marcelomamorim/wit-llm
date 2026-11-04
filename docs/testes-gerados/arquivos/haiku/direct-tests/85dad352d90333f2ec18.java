/*
 * @test
 * @summary Basic tests for the sun.security.x509.OIDName(DerValue) constructor
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main OIDNameWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.OIDName;

public class OIDNameWitupTest {

    public static void main(String[] args) throws Exception {
        testValidDerOid();
        testMalformedDerThrowsIOException();
        testEqualsWithSameOidFromDer();
    }

    static void testValidDerOid() throws Exception {
        // DER encoding of OID 1.2.3: tag 0x06, length 2, content 0x2A 0x03
        byte[] enc = {0x06, 0x02, 0x2A, 0x03};
        OIDName name = new OIDName(new DerValue(enc));
        String s = name.getOID().toString();
        if (!"1.2.3".equals(s)) {
            throw new RuntimeException("Expected OID 1.2.3 but got " + s);
        }
    }

    static void testMalformedDerThrowsIOException() throws Exception {
        // INTEGER tag (0x02) is not an OID tag; constructor must reject it
        byte[] bad = {0x02, 0x01, 0x05};
        DerValue dv = new DerValue(bad);
        try {
            new OIDName(dv);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for non-OID DER value");
    }

    static void testEqualsWithSameOidFromDer() throws Exception {
        byte[] enc = {0x06, 0x02, 0x2A, 0x03};
        OIDName fromDer = new OIDName(new DerValue(enc));
        OIDName fromOid = new OIDName(new ObjectIdentifier("1.2.3"));
        if (!fromDer.equals(fromOid)) {
            throw new RuntimeException("OIDName from DER should equal OIDName from same OID");
        }
    }
}
