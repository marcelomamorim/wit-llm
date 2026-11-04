/* @test    @summary Test for CertificateExtensions constructor with DerInputStream input    @run main CertificateExtensionsWitupTest */

import sun.security.util.DerInputStream;
import sun.security.x509.CertificateExtensions;
import java.io.IOException;

public class CertificateExtensionsWitupTest {

    public static void main(String[] args) throws Exception {
        testValidInput();
        testIOExceptionOnInvalidTag();
        testIOExceptionOnReadFully();
        testIndexOutOfBounds();
    }

    private static void testValidInput() throws IOException {
        byte[] validBytes = new byte[] {0x30, 0x0A, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02, 0x02, 0x01, 0x03};
        DerInputStream dis = new DerInputStream(validBytes);
        try {
            new CertificateExtensions(dis);
            System.out.println("testValidInput passed");
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testValidInput", e);
        }
    }

    private static void testIOExceptionOnInvalidTag() {
        byte[] invalidTagBytes = new byte[] {0x31, 0x0A, 0x02, 0x01, 0x01}; // Tag != 0x30
        DerInputStream dis = new DerInputStream(invalidTagBytes);
        try {
            new CertificateExtensions(dis);
            System.err.println("testIOExceptionOnInvalidTag failed: expected IOException");
        } catch (IOException e) {
            System.out.println("testIOExceptionOnInvalidTag passed");
        }
    }

    private static void testIOExceptionOnReadFully() {
        byte[] incompleteBytes = new byte[] {0x30, 0x02, 0x02}; // Incomplete data for readFully
        DerInputStream dis = new DerInputStream(incompleteBytes);
        try {
            new CertificateExtensions(dis);
            System.err.println("testIOExceptionOnReadFully failed: expected IOException");
        } catch (IOException e) {
            System.out.println("testIOExceptionOnReadFully passed");
        }
    }

    private static void testIndexOutOfBounds() {
        byte[] indexOutOfBoundsBytes = new byte[] {0x30, 0x00}; // Empty sequence
        DerInputStream dis = new DerInputStream(indexOutOfBoundsBytes);
        try {
            new CertificateExtensions(dis);
            System.out.println("testIndexOutOfBounds passed");
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testIndexOutOfBounds", e);
        }
    }
}
