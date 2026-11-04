/* @test    @summary Test delete method of CertificateAlgorithmId class    @run main CertificateAlgorithmIdWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.util.AlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidAlgorithm();
        testDeleteWithInvalidName();
        testDeleteWithNullName();
    }

    private static void testDeleteWithValidAlgorithm() throws Exception {
        AlgorithmId algId = AlgorithmId.get("SHA256withRSA");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);
        certAlgId.delete("algorithm");
        if (certAlgId.toString().contains("")) {
            throw new AssertionError("Expected algId to be null after delete") ;
        }
    }

    private static void testDeleteWithInvalidName() {
        AlgorithmId algId = AlgorithmId.get("SHA256withRSA");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);
        try {
            certAlgId.delete("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name") ;
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testDeleteWithNullName() {
        AlgorithmId algId = AlgorithmId.get("SHA256withRSA");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);
        try {
            certAlgId.delete(null);
            throw new AssertionError("Expected NullPointerException for null attribute name") ;
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
