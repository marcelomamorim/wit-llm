/*
 * @test
 * @summary Test CertificateAlgorithmId constructor with DerInputStream
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.AlgorithmId;

public class CertificateAlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        testValidDerInputStream();
        testInvalidDerInputStream();
    }

    private static void testValidDerInputStream() throws IOException {
        // Create a valid AlgorithmId DER encoding for SHA-1 OID (1.3.14.3.2.26)
        // AlgorithmId.get has a static method to get AlgorithmId by OID string
        AlgorithmId algId = AlgorithmId.get("1.3.14.3.2.26");
        byte[] encoded = algId.getEncoded();

        DerInputStream derIn = new DerInputStream(encoded);
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(derIn);

        String toString = certAlgId.toString();
        if (toString == null || toString.isEmpty()) {
            throw new RuntimeException("toString() returned null or empty string");
        }
        if (!toString.contains("OID = 1.3.14.3.2.26")) {
            throw new RuntimeException("toString() does not contain expected OID");
        }
    }

    private static void testInvalidDerInputStream() {
        // Provide invalid DER bytes to cause IOException on getDerValue or parse
        byte[] invalidBytes = new byte[] {0x01, 0x02, 0x03};
        DerInputStream derIn = new DerInputStream(invalidBytes);
        try {
            new CertificateAlgorithmId(derIn);
            throw new RuntimeException("Expected IOException not thrown for invalid DER input");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
