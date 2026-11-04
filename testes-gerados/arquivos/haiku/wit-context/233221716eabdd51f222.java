/*
 * @test
 * @summary Round-trip and null-state checks for CertificateAlgorithmId.encode(OutputStream)
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    private static final byte[] SHA256_ALG_ID = {
        0x30, 0x0D,
        0x06, 0x09, 0x60, (byte) 0x86, 0x48, 0x01, 0x65, 0x03, 0x04, 0x02, 0x01,
        0x05, 0x00
    };

    public static void main(String[] args) throws Exception {
        testEncodeRoundTrip();
        testEncodeIsDeterministic();
        testEncodeNullAlgorithmThrowsNpe();
    }

    private static void testEncodeRoundTrip() throws IOException {
        CertificateAlgorithmId id =
            new CertificateAlgorithmId(new ByteArrayInputStream(SHA256_ALG_ID));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        id.encode(out);
        byte[] encoded = out.toByteArray();
        if (!java.util.Arrays.equals(SHA256_ALG_ID, encoded)) {
            throw new RuntimeException("encode did not round-trip the DER AlgorithmId");
        }
    }

    private static void testEncodeIsDeterministic() throws IOException {
        CertificateAlgorithmId id =
            new CertificateAlgorithmId(new ByteArrayInputStream(SHA256_ALG_ID));
        ByteArrayOutputStream first = new ByteArrayOutputStream();
        ByteArrayOutputStream second = new ByteArrayOutputStream();
        id.encode(first);
        id.encode(second);
        if (!java.util.Arrays.equals(first.toByteArray(), second.toByteArray())) {
            throw new RuntimeException("repeated encode produced different bytes");
        }
    }

    private static void testEncodeNullAlgorithmThrowsNpe() {
        CertificateAlgorithmId id = new CertificateAlgorithmId((AlgorithmId) null);
        boolean thrown = false;
        try {
            id.encode(new ByteArrayOutputStream());
        } catch (NullPointerException e) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
        if (!thrown) {
            throw new RuntimeException("encode with null algId did not throw NullPointerException");
        }
    }
}
