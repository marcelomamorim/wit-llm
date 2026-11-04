/*
 * @test
 * @summary Test sun.security.util.DerInputStream.getOID() method
 * @run main sun.security.util.DerInputStreamWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class DerInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with valid DER encoding of an OID: 1.2.840.113549 (iso(1) member-body(2) us(840) rsadsi(113549))
        // DER encoding for OID 1.2.840.113549 is 06 06 2a 86 48 86 f7 0d
        byte[] oidEncoded = new byte[] { 0x06, 0x06, 0x2a, (byte)0x86, 0x48, (byte)0x86, (byte)0xf7, 0x0d };
        DerInputStream derIn = new DerInputStream(oidEncoded);
        ObjectIdentifier oid = derIn.getOID();
        String oidStr = oid.toString();
        if (!oidStr.equals("1.2.840.113549")) {
            throw new RuntimeException("Expected OID 1.2.840.113549 but got " + oidStr);
        }

        // Test with empty input - should throw IOException because no valid OID can be parsed
        try {
            DerInputStream emptyDer = new DerInputStream(new byte[0]);
            emptyDer.getOID();
            throw new RuntimeException("Expected IOException for empty input but none thrown");
        } catch (IOException e) {
            // Expected
        }

        // Test with invalid tag (not OID tag 0x06) - should throw IOException
        try {
            byte[] invalidTag = new byte[] { 0x05, 0x00 }; // NULL tag with zero length
            DerInputStream derInvalid = new DerInputStream(invalidTag);
            derInvalid.getOID();
            throw new RuntimeException("Expected IOException for invalid tag but none thrown");
        } catch (IOException e) {
            // Expected
        }
    }
}
