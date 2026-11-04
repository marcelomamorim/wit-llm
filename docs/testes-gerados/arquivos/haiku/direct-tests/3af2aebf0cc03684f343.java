/*
 * @test
 * @summary Verifies CertificateAlgorithmId.delete(String) accepts the ALGORITHM name case-insensitively and rejects unknown names with IOException
 * @modules java.base/sun.security.x509
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.IOException;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteAlgorithmNameClearsValue();
        testDeleteIsCaseInsensitive();
        testDeleteUnknownNameThrows();
        testDeleteIdentifierNameThrows();
        testDeleteOnNullAlgIdIsIdempotent();
    }

    static void testDeleteAlgorithmNameClearsValue() throws Exception {
        CertificateAlgorithmId c = new CertificateAlgorithmId((AlgorithmId) null);
        c.delete(CertificateAlgorithmId.ALGORITHM);
        if (!c.toString().equals("")) {
            throw new RuntimeException("toString should be empty after delete: " + c);
        }
    }

    static void testDeleteIsCaseInsensitive() throws Exception {
        CertificateAlgorithmId c = new CertificateAlgorithmId((AlgorithmId) null);
        c.delete("Algorithm");
        c.delete("ALGORITHM");
        if (!c.toString().equals("")) {
            throw new RuntimeException("unexpected toString: " + c);
        }
    }

    static void testDeleteUnknownNameThrows() throws Exception {
        CertificateAlgorithmId c = new CertificateAlgorithmId((AlgorithmId) null);
        boolean thrown = false;
        try {
            c.delete("unknownAttribute");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IOException expected for unknown name");
        }
    }

    static void testDeleteIdentifierNameThrows() throws Exception {
        CertificateAlgorithmId c = new CertificateAlgorithmId((AlgorithmId) null);
        boolean thrown = false;
        try {
            c.delete(CertificateAlgorithmId.IDENT);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IOException expected for IDENT name");
        }
    }

    static void testDeleteOnNullAlgIdIsIdempotent() throws Exception {
        CertificateAlgorithmId c = new CertificateAlgorithmId((AlgorithmId) null);
        c.delete(CertificateAlgorithmId.ALGORITHM);
        c.delete(CertificateAlgorithmId.ALGORITHM);
        if (!c.toString().equals("")) {
            throw new RuntimeException("toString should remain empty");
        }
    }
}
