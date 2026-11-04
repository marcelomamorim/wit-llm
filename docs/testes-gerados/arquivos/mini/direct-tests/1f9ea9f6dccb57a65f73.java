/*
 * @test
 * @summary Test CertificateAlgorithmId.get(String) method behavior
 * @run main CertificateAlgorithmIdWitupTest
 */

import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.AlgorithmId;
import java.io.IOException;

public class CertificateAlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        AlgorithmId alg = AlgorithmId.get("SHA1withDSA");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(alg);
        AlgorithmId result = certAlgId.get(CertificateAlgorithmId.ALGORITHM);
        if (!result.equals(alg)) {
            throw new RuntimeException("Returned AlgorithmId does not match the one set");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            AlgorithmId alg = AlgorithmId.get("SHA1withDSA");
            CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(alg);
            certAlgId.get("invalidName");
            throw new RuntimeException("Expected IOException was not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
