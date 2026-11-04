/*
 * @test
 * @summary Verifies CertificateAlgorithmId(DerInputStream) decoding, round-trip encoding and rejection of non-sequence DER input
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        AlgorithmId alg = AlgorithmId.get("SHA-256");

        DerOutputStream dos = new DerOutputStream();
        alg.encode(dos);
        byte[] der = dos.toByteArray();

        CertificateAlgorithmId fromDer =
                new CertificateAlgorithmId(new DerInputStream(der));
        CertificateAlgorithmId direct = new CertificateAlgorithmId(alg);

        if (!direct.toString().equals(fromDer.toString())) {
            throw new RuntimeException("toString mismatch: [" + direct
                    + "] vs [" + fromDer + "]");
        }
        if (fromDer.toString().isEmpty()) {
            throw new RuntimeException("toString should not be empty");
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        fromDer.encode(bos);
        if (!Arrays.equals(der, bos.toByteArray())) {
            throw new RuntimeException("round-trip encoding differs");
        }

        boolean thrown = false;
        try {
            new CertificateAlgorithmId(
                    new DerInputStream(new byte[] {0x05, 0x00}));
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IOException for non-sequence DER");
        }
    }
}
