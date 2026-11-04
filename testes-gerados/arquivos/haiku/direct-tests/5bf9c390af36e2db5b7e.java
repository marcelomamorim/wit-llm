/*
 * @test
 * @summary Verifies DER encoding of CertificateSerialNumber via encode(OutputStream) and round-trip decoding
 * @modules java.base/sun.security.x509
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Arrays;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] enc1 = encode(new CertificateSerialNumber(1));
        check(Arrays.equals(enc1, new byte[]{2, 1, 1}),
              "int 1 must encode as INTEGER 0x01");

        byte[] enc256 = encode(new CertificateSerialNumber(BigInteger.valueOf(256)));
        check(Arrays.equals(enc256, new byte[]{2, 2, 1, 0}),
              "BigInteger 256 must encode as INTEGER 0x0100");

        BigInteger big = new BigInteger("123456789012345678901234567890");
        byte[] encBig = encode(new CertificateSerialNumber(big));
        CertificateSerialNumber decoded =
                new CertificateSerialNumber(new ByteArrayInputStream(encBig));
        byte[] reEncoded = encode(decoded);
        check(Arrays.equals(encBig, reEncoded),
              "round trip through InputStream constructor must preserve encoding");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        new CertificateSerialNumber(1).encode(bos);
        check(bos.size() == enc1.length,
              "encode must write exactly the DER bytes once");
        check(Arrays.equals(bos.toByteArray(), enc1),
              "encode output must equal the DER bytes for value 1");
    }

    private static byte[] encode(CertificateSerialNumber csn) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        csn.encode(bos);
        return bos.toByteArray();
    }

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
