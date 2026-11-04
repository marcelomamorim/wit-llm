/*
 * @test
 * @summary Test sun.security.x509.CertificateAlgorithmId.encode(OutputStream) method
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithValidAlgorithmId();
    }

    private static void testEncodeWithValidAlgorithmId() throws IOException {
        // Use a known AlgorithmId instance (e.g., SHA-1 OID) to create CertificateAlgorithmId
        AlgorithmId algId = AlgorithmId.get("SHA1");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        certAlgId.encode(baos);
        byte[] encoded = baos.toByteArray();

        // The encoded output should not be empty
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded output is empty");
        }

        // The encoded output should start with DER SEQUENCE tag (0x30)
        if (encoded[0] != 0x30) {
            throw new AssertionError("Encoded output does not start with DER SEQUENCE tag");
        }
    }
}
