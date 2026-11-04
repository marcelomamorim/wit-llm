/*
 * @test
 * @summary Verifies CertificateAlgorithmId.encode behavior when the algorithm identifier is null
 * @modules java.base/sun.security.x509
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.ByteArrayOutputStream;
import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        CertificateAlgorithmId id = new CertificateAlgorithmId((AlgorithmId) null);

        if (!id.toString().equals("")) {
            throw new RuntimeException("toString() with null algId should be empty");
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        boolean npe = false;
        try {
            id.encode(bos);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("encode() with null algId should throw NullPointerException");
        }
        if (bos.size() != 0) {
            throw new RuntimeException("encode() must not write bytes when algId is null");
        }
    }
}
