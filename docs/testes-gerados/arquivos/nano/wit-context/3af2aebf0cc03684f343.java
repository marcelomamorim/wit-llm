/*
 * @test
 * @summary Test for delete method in sun.security.x509.CertificateAlgorithmId
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.util.AlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithAlgorithmName();
        testDeleteWithOtherName();
    }

    private static void testDeleteWithAlgorithmName() throws Exception {
        AlgorithmId algId = AlgorithmId.getInstance(AlgorithmId.SHA1_oid);
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);
        certAlgId.delete("algorithm");
        if (certAlgId.toString().contains("")) {
            System.out.println("testDeleteWithAlgorithmName passed");
        } else {
            throw new AssertionError("Expected delete to set algId to null");
        }
    }

    private static void testDeleteWithOtherName() throws Exception {
        AlgorithmId algId = AlgorithmId.getInstance(AlgorithmId.SHA1_oid);
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);
        try {
            certAlgId.delete("other");
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            if (e.getMessage().contains("Attribute name not recognized")) {
                System.out.println("testDeleteWithOtherName passed");
            } else {
                throw e;
            }
        }
    }
}
