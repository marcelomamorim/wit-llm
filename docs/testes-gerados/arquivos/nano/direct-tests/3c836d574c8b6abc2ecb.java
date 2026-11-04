/* @test    @summary Test sun.security.x509.OIDName.encode method for correct OID encoding    @run main OIDNameWitupTest */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.x509.OIDName;

public class OIDNameWitupTest {

    public static void main(String[] args) throws IOException {
        testEncodeWithValidOID();
        testEncodeWithCustomOID();
    }

    private static void testEncodeWithValidOID() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        OIDName oidName = new OIDName(oid);
        DerOutputStream out = new DerOutputStream();
        oidName.encode(out);
        byte[] encoded = out.toByteArray();
        // Expect the encoded bytes to start with the DER encoding for OID
        // DER encoding for OID tag is 0x06
        assert encoded[0] == 0x06 : "Expected DER tag 0x06 for OID";
        // Additional checks can be added based on DER encoding rules
    }

    private static void testEncodeWithCustomOID() throws IOException {
        ObjectIdentifier customOid = new ObjectIdentifier("2.5.29.19");
        OIDName oidName = new OIDName(customOid);
        DerOutputStream out = new DerOutputStream();
        oidName.encode(out);
        byte[] encoded = out.toByteArray();
        assert encoded[0] == 0x06 : "Expected DER tag 0x06 for OID";
    }
}
