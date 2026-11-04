/*
 * @test
 * @summary Witup generated test for CertificateSerialNumber.encode(OutputStream)
 * @modules java.base/sun.security.x509
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        checkEncoding(new CertificateSerialNumber(5), new byte[] {0x02, 0x01, 0x05});
        checkEncoding(new CertificateSerialNumber(BigInteger.valueOf(256)),
                new byte[] {0x02, 0x02, 0x01, 0x00});
        checkEncoding(new CertificateSerialNumber(BigInteger.valueOf(127)),
                new byte[] {0x02, 0x01, 0x7f});

        byte[] expected = new byte[] {0x02, 0x01, 0x05};
        CertificateSerialNumber decoded =
                new CertificateSerialNumber(new ByteArrayInputStream(expected));
        checkEncoding(decoded, expected);
        checkEncoding(decoded, expected);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(0xAA);
        new CertificateSerialNumber(5).encode(out);
        byte[] appended = out.toByteArray();
        byte[] expectedAppended = new byte[] {(byte) 0xAA, 0x02, 0x01, 0x05};
        if (!Arrays.equals(appended, expectedAppended)) {
            throw new RuntimeException("encode did not append to existing stream content");
        }
    }

    private static void checkEncoding(CertificateSerialNumber csn, byte[] expected)
            throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        csn.encode(out);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(actual, expected)) {
            throw new RuntimeException("encode mismatch: expected "
                    + Arrays.toString(expected) + " but was "
                    + Arrays.toString(actual));
        }
    }
}
