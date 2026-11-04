/*
 * @test
 * @summary Test for sun.security.x509.CertificateAlgorithmId.encode(OutputStream)
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.util.AlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws IOException {
        testEncodeNormal();
        testEncodeNullAlgId();
    }

    private static void testEncodeNormal() throws IOException {
        AlgorithmId algId = AlgorithmId.getInstance("SHA256withRSA");
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        certAlgId.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding produced empty byte array");
        }
    }

    private static void testEncodeNullAlgId() {
        try {
            CertificateAlgorithmId certAlgId = new CertificateAlgorithmId((AlgorithmId) null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            certAlgId.encode(baos);
            throw new AssertionError("Expected NullPointerException or IOException not thrown");
        } catch (NullPointerException | IOException e) {
            // Expected exception
        }
    }
}
