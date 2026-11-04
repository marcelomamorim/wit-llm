/*
 * @test
 * @summary Verifies DerValue.getOID returns the decoded OID for ObjectId tags and throws IOException for other tags
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class DerValueWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] rsaOidEncoding = {
            0x06, 0x09, 0x2A, (byte)0x86, 0x48, (byte)0x86, (byte)0xF7, 0x0D, 0x01, 0x01, 0x01
        };
        String rsaOid = "1.2.840.113549.1.1.1";

        DerValue fromTlv = new DerValue(rsaOidEncoding);
        ObjectIdentifier oid1 = fromTlv.getOID();
        check(oid1 != null, "getOID returned null for ObjectId TLV");
        check(rsaOid.equals(oid1.toString()),
              "unexpected OID from TLV: " + oid1);

        byte[] contents = {
            0x2A, (byte)0x86, 0x48, (byte)0x86, (byte)0xF7, 0x0D, 0x01, 0x01, 0x01
        };
        DerValue fromTagAndData = new DerValue(DerValue.tag_ObjectId, contents);
        ObjectIdentifier oid2 = fromTagAndData.getOID();
        check(rsaOid.equals(oid2.toString()),
              "unexpected OID from tag/data constructor: " + oid2);

        ObjectIdentifier oid3 = fromTlv.getOID();
        check(rsaOid.equals(oid3.toString()), "repeated getOID changed result");

        DerValue integerValue = new DerValue(new byte[] {0x02, 0x01, 0x05});
        boolean threw = false;
        try {
            integerValue.getOID();
        } catch (IOException e) {
            threw = true;
        }
        check(threw, "getOID on INTEGER should throw IOException");
    }
}
