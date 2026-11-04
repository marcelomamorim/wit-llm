/*
 * @test
 * @summary Checks CertificateAlgorithmId.delete(String) accepts the "algorithm" name (case-insensitive) and rejects other names with IOException
 * @modules java.base/sun.security.x509
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.IOException;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteAlgorithmName();
        testDeleteAlgorithmNameIgnoresCase();
        testDeleteUnknownNameThrows();
        testDeleteEmptyNameThrows();
    }

    private static void testDeleteAlgorithmName() throws IOException {
        CertificateAlgorithmId id = new CertificateAlgorithmId((AlgorithmId) null);
        id.delete(CertificateAlgorithmId.ALGORITHM);
        if (!"".equals(id.toString())) {
            throw new RuntimeException("toString should be empty after delete(ALGORITHM)");
        }
    }

    private static void testDeleteAlgorithmNameIgnoresCase() throws IOException {
        CertificateAlgorithmId id = new CertificateAlgorithmId((AlgorithmId) null);
        id.delete("ALGORITHM");
        id.delete("Algorithm");
        if (!"".equals(id.toString())) {
            throw new RuntimeException("toString should be empty after case-variant delete");
        }
    }

    private static void testDeleteUnknownNameThrows() {
        CertificateAlgorithmId id = new CertificateAlgorithmId((AlgorithmId) null);
        expectIOException(id, "unknownName");
        expectIOException(id, CertificateAlgorithmId.IDENT);
    }

    private static void testDeleteEmptyNameThrows() {
        CertificateAlgorithmId id = new CertificateAlgorithmId((AlgorithmId) null);
        expectIOException(id, "");
    }

    private static void expectIOException(CertificateAlgorithmId id, String name) {
        try {
            id.delete(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name: " + name);
    }
}
