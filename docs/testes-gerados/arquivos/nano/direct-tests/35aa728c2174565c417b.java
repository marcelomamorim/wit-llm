/* @test    @summary Test constructor CertificateExtensions(DerInputStream in) with valid and invalid input    @run main CertificateExtensionsWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateExtensions;

public class CertificateExtensionsWitupTest {

    public static void main(String[] args) {
        testValidInput();
        testEmptyInput();
        testInvalidInput();
    }

    private static void testValidInput() {
        byte[] validBytes = new byte[] {0x30, 0x00}; // DER sequence with length 0
        try {
            DerInputStream dis = new DerInputStream(validBytes);
            CertificateExtensions ce = new CertificateExtensions(dis);
            System.out.println("testValidInput: Passed");
        } catch (IOException e) {
            System.err.println("testValidInput: Failed with IOException: " + e.getMessage());
        }
    }

    private static void testEmptyInput() {
        byte[] emptyBytes = new byte[0];
        try {
            DerInputStream dis = new DerInputStream(emptyBytes);
            CertificateExtensions ce = new CertificateExtensions(dis);
            System.out.println("testEmptyInput: Failed - expected IOException");
        } catch (IOException e) {
            System.out.println("testEmptyInput: Passed with expected IOException: " + e.getMessage());
        }
    }

    private static void testInvalidInput() {
        byte[] invalidBytes = new byte[] {0xFF, 0xFF}; // invalid DER encoding
        try {
            DerInputStream dis = new DerInputStream(invalidBytes);
            CertificateExtensions ce = new CertificateExtensions(dis);
            System.out.println("testInvalidInput: Failed - expected IOException");
        } catch (IOException e) {
            System.out.println("testInvalidInput: Passed with expected IOException: " + e.getMessage());
        }
    }
}
