/*
 * @test
 * @summary Round-trip DER decoding through CertificateSerialNumber(DerInputStream)
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static CertificateSerialNumber roundTrip(CertificateSerialNumber original) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        original.encode(bos);
        byte[] der = bos.toByteArray();
        DerInputStream in = new DerInputStream(der);
        return new CertificateSerialNumber(in);
    }

    public static void main(String[] args) throws Exception {
        CertificateSerialNumber small = new CertificateSerialNumber(BigInteger.valueOf(42));
        CertificateSerialNumber decodedSmall = roundTrip(small);
        check(small.toString().equals(decodedSmall.toString()),
              "small round-trip mismatch: " + small + " vs " + decodedSmall);

        BigInteger large = new BigInteger("123456789abcdef0123456789abcdef", 16);
        CertificateSerialNumber bigOrig = new CertificateSerialNumber(large);
        CertificateSerialNumber decodedBig = roundTrip(bigOrig);
        check(bigOrig.toString().equals(decodedBig.toString()),
              "large round-trip mismatch: " + bigOrig + " vs " + decodedBig);

        CertificateSerialNumber intOrig = new CertificateSerialNumber(7);
        CertificateSerialNumber decodedInt = roundTrip(intOrig);
        check(intOrig.toString().equals(decodedInt.toString()),
              "int round-trip mismatch: " + intOrig + " vs " + decodedInt);

        CertificateSerialNumber fromBig = new CertificateSerialNumber(BigInteger.valueOf(7));
        check(fromBig.toString().equals(intOrig.toString()),
              "int and BigInteger constructors should agree for 7");

        check(decodedSmall.toString() != null && !decodedSmall.toString().isEmpty(),
              "decoded toString should not be empty");
    }
}
