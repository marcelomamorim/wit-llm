/*
 * @test
 * @summary Round-trip and encoding consistency for CertificateSerialNumber public API
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
    public static void main(String[] args) throws IOException {
        byte[] fromInt = encode(new CertificateSerialNumber(12345));
        byte[] fromBig = encode(new CertificateSerialNumber(BigInteger.valueOf(12345)));
        check(Arrays.equals(fromInt, fromBig), "int and BigInteger encodings differ");

        byte[] other = encode(new CertificateSerialNumber(12346));
        check(!Arrays.equals(fromInt, other), "different serial numbers encoded equally");

        CertificateSerialNumber decoded =
            new CertificateSerialNumber(new ByteArrayInputStream(fromInt));
        check(Arrays.equals(fromInt, encode(decoded)), "round trip changed encoding");
        check(!decoded.toString().isEmpty(), "toString of decoded value is empty");
        check(!new CertificateSerialNumber(1).toString().isEmpty(), "toString is empty");
    }

    private static byte[] encode(CertificateSerialNumber csn) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        csn.encode(baos);
        return baos.toByteArray();
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
