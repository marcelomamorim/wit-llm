/*
 * @test
 * @summary Verify CertificatePolicyId(DerValue) decodes the policy OID and rejects non-OID DER values
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
        testDecodesOid();
        testRoundTripEquality();
        testNonOidTagRejected();
    }

    static void testDecodesOid() throws Exception {
        ObjectIdentifier oid = ObjectIdentifier.of("1.2.3.4");
        DerOutputStream out = new DerOutputStream();
        out.putOID(oid);
        DerValue val = new DerValue(out.toByteArray());

        CertificatePolicyId id = new CertificatePolicyId(val);
        if (!oid.equals(id.getIdentifier())) {
            throw new RuntimeException("Decoded identifier mismatch: " + id.getIdentifier());
        }
    }

    static void testRoundTripEquality() throws Exception {
        ObjectIdentifier oid = ObjectIdentifier.of("2.5.29.32.0");
        DerOutputStream out = new DerOutputStream();
        out.putOID(oid);
        DerValue val = new DerValue(out.toByteArray());

        CertificatePolicyId fromDer = new CertificatePolicyId(val);
        CertificatePolicyId direct = new CertificatePolicyId(oid);
        if (!fromDer.equals(direct)) {
            throw new RuntimeException("DER-decoded id not equal to direct id");
        }
        if (fromDer.hashCode() != direct.hashCode()) {
            throw new RuntimeException("hashCode mismatch for equal ids");
        }
    }

    static void testNonOidTagRejected() throws Exception {
        // DER INTEGER 5: tag is not ObjectIdentifier, so getOID() must fail
        DerValue val = new DerValue(new byte[] {0x02, 0x01, 0x05});
        boolean thrown = false;
        try {
            new CertificatePolicyId(val);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for non-OID DER value");
        }
    }
}
