/* @test    @summary Test for CertificateSerialNumber constructor with DerInputStream to cover exception paths.    @run main CertificateSerialNumberWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateSerialNumber;

public class CertificateSerialNumberWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithDerInputStream_EOF();
        testConstructorWithDerInputStream_Invalid();
    }

    private static void testConstructorWithDerInputStream_EOF() {
        byte[] emptyBuffer = new byte[0];
        DerInputStream dis = new DerInputStream(emptyBuffer);
        try {
            new CertificateSerialNumber(dis);
            System.out.println("testConstructorWithDerInputStream_EOF: Failed - no exception");
        } catch (IOException e) {
            System.out.println("testConstructorWithDerInputStream_EOF: Passed - caught IOException");
        }
    }

    private static void testConstructorWithDerInputStream_Invalid() {
        byte[] invalidBuffer = {0x01, 0x02, 0x03};
        DerInputStream dis = new DerInputStream(invalidBuffer);
        try {
            new CertificateSerialNumber(dis);
            System.out.println("testConstructorWithDerInputStream_Invalid: Failed - no exception");
        } catch (IOException e) {
            System.out.println("testConstructorWithDerInputStream_Invalid: Passed - caught IOException");
        }
    }
}
