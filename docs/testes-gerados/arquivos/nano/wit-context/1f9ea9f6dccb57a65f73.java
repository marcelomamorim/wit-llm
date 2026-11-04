/*
 * @test
 * @summary Test for sun.security.x509.CertificateAlgorithmId.get(String)
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.util.AlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        AlgorithmId algId = AlgorithmId.get("1.2.840.113549.1.1.5");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);
        String name = "algorithm";
        AlgorithmId result = certAlgId.get(name);
        if (result == null) {
            throw new AssertionError("Expected non-null AlgorithmId for name '" + name + "'");
        }
        if (!result.equals(algId)) {
            throw new AssertionError("Returned AlgorithmId does not match the original");
        }
        System.out.println("testGetWithValidName passed");
    }

    private static void testGetWithInvalidName() {
        AlgorithmId algId = AlgorithmId.get("1.2.840.113549.1.1.5");
        CertificateAlgorithmId certAlgId;
        try {
            certAlgId = new CertificateAlgorithmId(algId);
        } catch (IOException e) {
            throw new AssertionError("Failed to create CertificateAlgorithmId", e);
        }
        String invalidName = "invalidName";
        try {
            certAlgId.get(invalidName);
            throw new AssertionError("Expected IOException for name '" + invalidName + "'");
        } catch (IOException e) {
            String expectedMessage = "Attribute name not recognized by CertAttrSet:CertificateAlgorithmId.";
            if (!e.getMessage().equals(expectedMessage)) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        System.out.println("testGetWithInvalidName passed");
    }
}
