/* @test    @summary Test the encode method of CertificatePolicyId for correct DER encoding.    @run main CertificatePolicyIdWitupTest */

import java.io.IOException;
import sun.security.x509.CertificatePolicyId;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;

public class CertificatePolicyIdWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithValidOID();
        testEncodeWithDifferentOID();
    }

    private static void testEncodeWithValidOID() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        CertificatePolicyId cpid = new CertificatePolicyId(oid);
        DerOutputStream out = new DerOutputStream();
        cpid.encode(out);
        byte[] encoded = out.toByteArray();
        // Expected DER encoding: 06 03 2a 03 04 (OID tag + length + OID bytes)
        byte[] expected = {0x06, 0x03, 0x2a, 0x03, 0x04};
        boolean matches = java.util.Arrays.equals(encoded, expected);
        if (!matches) {
            throw new AssertionError("Encoding mismatch for valid OID. Expected: " + bytesToHex(expected) + ", Got: " + bytesToHex(encoded));
        }
    }

    private static void testEncodeWithDifferentOID() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.29.32");
        CertificatePolicyId cpid = new CertificatePolicyId(oid);
        DerOutputStream out = new DerOutputStream();
        cpid.encode(out);
        byte[] encoded = out.toByteArray();
        byte[] expected = {0x06, 0x03, 0x55, 0x1f, 0x20};
        boolean matches = java.util.Arrays.equals(encoded, expected);
        if (!matches) {
            throw new AssertionError("Encoding mismatch for different OID. Expected: " + bytesToHex(expected) + ", Got: " + bytesToHex(encoded));
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x ", b));
        }
        return sb.toString().trim();
    }
}
