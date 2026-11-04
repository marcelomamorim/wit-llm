/*
 * @test
 * @summary Test encode method of CertificateAlgorithmId for correct encoding and exception safety
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithValidAlgorithmId();
        testEncodeWithNullAlgorithmId();
    }

    private static void testEncodeWithValidAlgorithmId() throws IOException {
        // Use a known AlgorithmId instance (e.g., SHA-256 OID) to test encoding
        AlgorithmId algId = AlgorithmId.get("SHA256");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        certAlgId.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded output should not be null or empty");
        }

        // Basic sanity check: encoded output should start with DER SEQUENCE tag (0x30)
        if (encoded[0] != 0x30) {
            throw new AssertionError("Encoded output does not start with expected DER SEQUENCE tag");
        }
    }

    private static void testEncodeWithNullAlgorithmId() {
        // Construct CertificateAlgorithmId with null AlgorithmId to test encode behavior
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId((AlgorithmId) null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            certAlgId.encode(baos);
            throw new AssertionError("Expected NullPointerException or IOException when encoding with null AlgorithmId");
        } catch (NullPointerException | IOException e) {
            // Expected exception due to null algId
        }
    }
}
