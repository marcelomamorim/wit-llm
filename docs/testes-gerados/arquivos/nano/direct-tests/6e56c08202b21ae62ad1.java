/* @test    @summary Test constructor CertificateAlgorithmId(DerInputStream in) for sun.security.x509.CertificateAlgorithmId    @run main CertificateAlgorithmIdWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.util.DerValue;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithValidDerValue();
        testConstructorWithInvalidDerValue();
    }

    private static void testConstructorWithValidDerValue() throws IOException {
        byte[] derBytes = {0x30, 0x0A, 0x06, 0x08, 0x2A, 0x86, 0x48, 0x86, 0xF7, 0x0D, 0x01, 0x01, 0x05};
        DerInputStream dis = new DerInputStream(derBytes);
        CertificateAlgorithmId caid = new CertificateAlgorithmId(dis);
        if (caid == null || caid.toString().isEmpty()) {
            throw new AssertionError("Constructor with valid DerInputStream returned null or empty string");
        }
        System.out.println("testConstructorWithValidDerValue passed.");
    }

    private static void testConstructorWithInvalidDerValue() {
        byte[] invalidDerBytes = {0x00, 0x00};
        ByteArrayInputStream bais = new ByteArrayInputStream(invalidDerBytes);
        try {
            DerInputStream dis = new DerInputStream(bais);
            new CertificateAlgorithmId(dis);
            throw new AssertionError("Expected IOException was not thrown for invalid DER bytes");
        } catch (IOException e) {
            System.out.println("testConstructorWithInvalidDerValue passed.");
        }
    }
}
