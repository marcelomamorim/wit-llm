/*
 * @test
 * @summary Checks the InputStream constructor of CertificateSerialNumber decodes DER serial numbers and rejects empty input
 * @modules java.base/sun.security.x509
 * @run main CertificateSerialNumberWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] der = {0x02, 0x01, 0x05};
        CertificateSerialNumber fromStream =
            new CertificateSerialNumber(new ByteArrayInputStream(der));
        CertificateSerialNumber fromInt = new CertificateSerialNumber(5);
        check(fromStream.toString().equals(fromInt.toString()),
              "DER-decoded serial should match int-constructed serial");

        byte[] derOther = {0x02, 0x01, 0x07};
        CertificateSerialNumber other =
            new CertificateSerialNumber(new ByteArrayInputStream(derOther));
        check(!other.toString().equals(fromInt.toString()),
              "different DER value should produce a different serial");

        boolean thrown = false;
        try {
            new CertificateSerialNumber(new ByteArrayInputStream(new byte[0]));
        } catch (IOException expected) {
            thrown = true;
        }
        check(thrown, "empty stream should raise IOException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
