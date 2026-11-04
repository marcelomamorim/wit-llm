/*
 * @test
 * @summary Test CertificateAlgorithmId.delete(String) method behavior
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.AlgorithmId;

public class CertificateAlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithAlgorithmName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithAlgorithmName() throws IOException {
        AlgorithmId algId = AlgorithmId.get("SHA1");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);
        certAlgId.delete("algorithm");
        if (certAlgId.toString().length() != 0) {
            throw new RuntimeException("delete did not nullify algId as expected");
        }
    }

    private static void testDeleteWithInvalidName() {
        AlgorithmId algId = AlgorithmId.get("SHA1");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);
        try {
            certAlgId.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
