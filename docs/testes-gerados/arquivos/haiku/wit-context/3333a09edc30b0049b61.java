/*
 * @test
 * @summary Witup-derived tests for CertificateSerialNumber(InputStream) constructor
 * @modules java.base/sun.security.x509
 * @run main CertificateSerialNumberWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {
    public static void main(String[] args) throws Exception {
        testRoundTripPositiveInteger();
        testWrongTagRejected();
        testEmptyStreamRejected();
    }

    static void testRoundTripPositiveInteger() throws IOException {
        byte[] der = {0x02, 0x01, 0x05};
        CertificateSerialNumber csn =
            new CertificateSerialNumber(new ByteArrayInputStream(der));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        csn.encode(out);
        if (!Arrays.equals(der, out.toByteArray())) {
            throw new RuntimeException("round trip of INTEGER 5 failed");
        }
    }

    static void testWrongTagRejected() {
        byte[] der = {0x04, 0x01, 0x05};
        boolean thrown = false;
        try {
            new CertificateSerialNumber(new ByteArrayInputStream(der));
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("non-INTEGER tag was accepted");
        }
    }

    static void testEmptyStreamRejected() {
        byte[] der = new byte[0];
        boolean thrown = false;
        try {
            new CertificateSerialNumber(new ByteArrayInputStream(der));
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("empty stream was accepted");
        }
    }
}
