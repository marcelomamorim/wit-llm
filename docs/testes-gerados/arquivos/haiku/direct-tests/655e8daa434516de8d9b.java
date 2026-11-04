/*
 * @test
 * @summary Verifies the DER encoding produced by CertificatePolicyId.encode and its round trip.
 * @modules java.base/sun.security.util java.base/sun.security.x509
 * @run main CertificatePolicyIdWitupTest
 */

import java.util.Arrays;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.CertificatePolicyId;

public class CertificatePolicyIdWitupTest {
    public static void main(String[] args) throws Exception {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.29.32");
        CertificatePolicyId policy = new CertificatePolicyId(oid);

        DerOutputStream out = new DerOutputStream();
        policy.encode(out);
        byte[] expected = {0x06, 0x03, 0x55, 0x1D, 0x20};
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Unexpected encoding for 2.5.29.32");
        }

        DerOutputStream out2 = new DerOutputStream();
        policy.encode(out2);
        if (!Arrays.equals(actual, out2.toByteArray())) {
            throw new RuntimeException("Encoding is not deterministic");
        }

        DerValue value = new DerValue(actual);
        if (!oid.equals(value.getOID())) {
            throw new RuntimeException("Decoded OID differs from encoded OID");
        }

        CertificatePolicyId parsed = new CertificatePolicyId(value);
        if (!oid.equals(parsed.getIdentifier())) {
            throw new RuntimeException("Round-trip identifier mismatch");
        }
        DerOutputStream out3 = new DerOutputStream();
        parsed.encode(out3);
        if (!Arrays.equals(actual, out3.toByteArray())) {
            throw new RuntimeException("Re-encoding parsed policy changed bytes");
        }

        ObjectIdentifier oid2 = new ObjectIdentifier("1.2.3");
        DerOutputStream out4 = new DerOutputStream();
        new CertificatePolicyId(oid2).encode(out4);
        byte[] expected2 = {0x06, 0x02, 0x2A, 0x03};
        if (!Arrays.equals(expected2, out4.toByteArray())) {
            throw new RuntimeException("Unexpected encoding for 1.2.3");
        }
    }
}
