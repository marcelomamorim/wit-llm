/* @test    @summary Test encoding of CertificatePolicyMap object    @run main CertificatePolicyMapWitupTest */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.x509.CertificatePolicyId;
import sun.security.x509.CertificatePolicyMap;

public class CertificatePolicyMapWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithValidIds();
        testEncodeWithNullIds();
    }

    static void testEncodeWithValidIds() throws IOException {
        CertificatePolicyId issuerId = new CertificatePolicyId();
        CertificatePolicyId subjectId = new CertificatePolicyId();
        CertificatePolicyMap map = new CertificatePolicyMap(issuerId, subjectId);
        DerOutputStream out = new DerOutputStream();
        map.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding returned null or empty byte array");
        }
    }

    static void testEncodeWithNullIds() throws IOException {
        CertificatePolicyId issuerId = null;
        CertificatePolicyId subjectId = null;
        try {
            CertificatePolicyMap map = new CertificatePolicyMap(issuerId, subjectId);
            DerOutputStream out = new DerOutputStream();
            map.encode(out);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
