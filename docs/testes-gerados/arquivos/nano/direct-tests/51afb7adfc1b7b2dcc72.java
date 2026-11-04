/* @test    @summary Test constructor CertificateX509Key(DerInputStream in) for sun.security.x509.CertificateX509Key    @run main CertificateX509KeyWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithValidDerInput();
        testConstructorWithEmptyDerInput();
    }

    private static void testConstructorWithValidDerInput() throws IOException {
        byte[] validDerBytes = {0x30, 0x0A, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02, 0x02, 0x01, 0x03};
        DerInputStream derInputStream = new DerInputStream(validDerBytes);
        try {
            CertificateX509Key certKey = new CertificateX509Key(derInputStream);
            if (certKey != null && certKey.toString() != null) {
                System.out.println("testConstructorWithValidDerInput: PASSED");
            } else {
                System.out.println("testConstructorWithValidDerInput: FAILED - null object or toString() returned null");
            }
        } catch (IOException e) {
            System.out.println("testConstructorWithValidDerInput: FAILED with IOException: " + e.getMessage());
        }
    }

    private static void testConstructorWithEmptyDerInput() {
        byte[] emptyBytes = {};
        ByteArrayInputStream bais = new ByteArrayInputStream(emptyBytes);
        DerInputStream derInputStream = null;
        try {
            derInputStream = new DerInputStream(bais);
            new CertificateX509Key(derInputStream);
            System.out.println("testConstructorWithEmptyDerInput: FAILED - expected IOException");
        } catch (IOException e) {
            System.out.println("testConstructorWithEmptyDerInput: PASSED");
        }
    }
}
