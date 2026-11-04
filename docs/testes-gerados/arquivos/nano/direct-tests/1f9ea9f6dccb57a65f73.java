/* @test    @summary Test for CertificateAlgorithmId.get(String) method    @run main CertificateAlgorithmIdWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.util.AlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        AlgorithmId algId = AlgorithmId.get("SHA256withRSA");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);
        AlgorithmId result = certAlgId.get("algorithm");
        if (result == null || !result.equals(algId)) {
            throw new AssertionError("Expected AlgorithmId to match the original.");
        }
    }

    private static void testGetWithInvalidName() {
        AlgorithmId algId = AlgorithmId.get("SHA256withRSA");
        CertificateAlgorithmId certAlgId;
        try {
            certAlgId = new CertificateAlgorithmId(algId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            certAlgId.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name.");
        } catch (IOException e) {
            String expectedMessage = "Attribute name not recognized by " +
                "CertAttrSet:CertificateAlgorithmId.";
            if (!e.getMessage().equals(expectedMessage)) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
