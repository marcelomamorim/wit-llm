/* @test    @summary Test encoding of CertificateAlgorithmId class in DER format    @run main CertificateAlgorithmIdWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.util.DerOutputStream;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws IOException {
        testEncodeWithValidAlgorithmId();
        testEncodeWithNullAlgorithmId();
    }

    private static void testEncodeWithValidAlgorithmId() throws IOException {
        AlgorithmId algId = AlgorithmId.get(AlgorithmId.SHA1_oid); // Using a known OID
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(algId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        certAlgId.encode(baos);
        byte[] encodedBytes = baos.toByteArray();
        // Basic check: encoded bytes should not be empty
        if (encodedBytes.length == 0) {
            throw new AssertionError("Encoding resulted in empty byte array");
        }
        // Optional: further checks can be added to verify DER structure
    }

    private static void testEncodeWithNullAlgorithmId() throws IOException {
        AlgorithmId nullAlgId = null;
        try {
            CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(nullAlgId);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            certAlgId.encode(baos);
            throw new AssertionError("Expected NullPointerException for null AlgorithmId");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
