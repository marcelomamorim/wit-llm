/*
 * @test
 * @summary Checks observable DER encoding behavior of CertificatePolicyId.encode
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificatePolicyIdWitupTest
 */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.CertificatePolicyId;

public class CertificatePolicyIdWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeKnownBytes();
        testEncodeRoundTrip();
        testEncodeAppendsToStream();
    }

    static void testEncodeKnownBytes() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.29.32");
        CertificatePolicyId policyId = new CertificatePolicyId(oid);
        DerOutputStream out = new DerOutputStream();
        policyId.encode(out);
        byte[] actual = out.toByteArray();
        byte[] expected = {0x06, 0x03, 0x55, 0x1D, 0x20};
        if (actual.length != expected.length) {
            throw new RuntimeException("Unexpected encoding length: " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new RuntimeException("Unexpected encoding byte at " + i + ": " + actual[i]);
            }
        }
    }

    static void testEncodeRoundTrip() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.840.113549.1.9.16");
        CertificatePolicyId original = new CertificatePolicyId(oid);
        DerOutputStream out = new DerOutputStream();
        original.encode(out);
        DerValue value = new DerValue(out.toByteArray());
        CertificatePolicyId decoded = new CertificatePolicyId(value);
        if (!oid.equals(decoded.getIdentifier())) {
            throw new RuntimeException("Round trip changed identifier: " + decoded.getIdentifier());
        }
        if (!original.equals(decoded)) {
            throw new RuntimeException("Round-tripped policy id is not equal to original");
        }
    }

    static void testEncodeAppendsToStream() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("2.5.29.32");
        CertificatePolicyId policyId = new CertificatePolicyId(oid);
        DerOutputStream out = new DerOutputStream();
        policyId.encode(out);
        policyId.encode(out);
        int length = out.toByteArray().length;
        if (length != 10) {
            throw new RuntimeException("Expected two encodings (10 bytes), got " + length);
        }
    }
}
