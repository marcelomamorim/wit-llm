/*
 * @test
 * @summary Witup generated test for DerValue.getOID: valid OID decoding and IOException for non-OID tags
 * @modules java.base/sun.security.util
 * @run main DerValueWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;

public class DerValueWitupTest {
    public static void main(String[] args) throws Exception {
        check("2.5.4.3", new byte[]{0x06, 0x03, 0x55, 0x04, 0x03});
        check("1.2.840.113549.1.1.1", new byte[]{0x06, 0x09, 0x2A, (byte)0x86, 0x48, (byte)0x86, (byte)0xF7, 0x0D, 0x01, 0x01, 0x01});
        expectNotOid(new byte[]{0x02, 0x01, 0x05});
        expectNotOid(new byte[]{0x05, 0x00});
    }

    static void check(String expected, byte[] encoded) throws Exception {
        String actual = new DerValue(encoded).getOID().toString();
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected OID " + expected + " but got " + actual);
        }
    }

    static void expectNotOid(byte[] encoded) throws Exception {
        DerValue value = new DerValue(encoded);
        try {
            value.getOID();
        } catch (IOException e) {
            if (e.getMessage() == null || !e.getMessage().contains("not an OID")) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
            return;
        }
        throw new RuntimeException("Expected IOException for non-OID tag");
    }
}
