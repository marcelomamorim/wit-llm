/*
 * @test
 * @summary Verify DerInputStream.getOID parses DER-encoded object identifiers
 * @modules java.base/sun.security.util
 * @run main DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.ObjectIdentifier;

public class DerInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSingleOid();
        testSequentialOids();
    }

    static void testSingleOid() throws Exception {
        byte[] rsaEncryption = {
            0x06, 0x09, 0x2A, (byte) 0x86, 0x48, (byte) 0x86,
            (byte) 0xF7, 0x0D, 0x01, 0x01, 0x01
        };
        DerInputStream in = new DerInputStream(rsaEncryption);
        ObjectIdentifier oid = in.getOID();
        check(oid != null, "getOID returned null");
        check("1.2.840.113549.1.1.1".equals(oid.toString()),
                "unexpected OID: " + oid);
    }

    static void testSequentialOids() throws Exception {
        byte[] data = {
            0x06, 0x03, 0x55, 0x04, 0x03,
            0x06, 0x03, 0x55, 0x04, 0x06
        };
        DerInputStream in = new DerInputStream(data);
        ObjectIdentifier first = in.getOID();
        check("2.5.4.3".equals(first.toString()),
                "unexpected first OID: " + first);
        ObjectIdentifier second = in.getOID();
        check("2.5.4.6".equals(second.toString()),
                "unexpected second OID: " + second);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
