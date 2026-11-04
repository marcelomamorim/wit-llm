/*
 * @test
 * @summary Test CertificateAlgorithmId.get(String) method behavior
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.IOException;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy AlgorithmId instance for testing
        // Use AlgorithmId.get with a known OID to get a valid instance
        AlgorithmId algId = AlgorithmId.get("1.2.840.113549.1.1.5"); // sha1WithRSAEncryption

        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);

        // Test get with valid name (case insensitive)
        AlgorithmId result1 = certAlgId.get("algorithm");
        if (!result1.equals(algId)) {
            throw new RuntimeException("get(algorithm) did not return expected AlgorithmId");
        }

        AlgorithmId result2 = certAlgId.get("ALGORITHM");
        if (!result2.equals(algId)) {
            throw new RuntimeException("get(ALGORITHM) did not return expected AlgorithmId");
        }

        // Test get with invalid name throws IOException
        boolean threw = false;
        try {
            certAlgId.get("invalidName");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        }

        System.out.println("Test passed.");
    }
}
