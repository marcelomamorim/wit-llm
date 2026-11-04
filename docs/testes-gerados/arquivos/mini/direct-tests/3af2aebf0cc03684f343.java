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
        testDeleteAlgorithm();
        testDeleteInvalidName();
    }

    private static void testDeleteAlgorithm() throws IOException {
        AlgorithmId alg = AlgorithmId.get("SHA1");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(alg);
        certAlgId.delete(CertificateAlgorithmId.ALGORITHM);
        // After deletion, toString should return empty string since algId is null
        String result = certAlgId.toString();
        if (!"".equals(result)) {
            throw new RuntimeException("delete(ALGORITHM) did not nullify algId as expected, got: " + result);
        }
    }

    private static void testDeleteInvalidName() {
        AlgorithmId alg = AlgorithmId.get("SHA1");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(alg);
        try {
            certAlgId.delete("invalidName");
            throw new RuntimeException("delete with invalid name did not throw IOException");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
