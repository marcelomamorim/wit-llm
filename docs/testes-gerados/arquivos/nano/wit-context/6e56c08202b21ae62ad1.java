/*
 * @test
 * @summary Tests for sun.security.x509.CertificateAlgorithmId constructors and exception paths
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithDerInputStream();
        testConstructorWithInputStream();
    }

    private static void testConstructorWithDerInputStream() {
        byte[] validBytes = {0x30, 0x0A, 0x06, 0x08, 0x2A, 0x86, 0x48, 0x86, 0xF7, 0x0D, 0x01, 0x01, 0x05};
        DerInputStream dis = new DerInputStream(validBytes);
        try {
            new CertificateAlgorithmId(dis);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid DerInputStream", e);
        }
        // Test with input that triggers IOException (simulate EOF)
        byte[] invalidBytes = {0x30, 0x01}; // incomplete DER
        try {
            DerInputStream invalidDis = new DerInputStream(invalidBytes);
            new CertificateAlgorithmId(invalidDis);
            throw new AssertionError("Expected IOException not thrown for invalid DerInputStream");
        } catch (IOException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithInputStream() {
        byte[] validBytes = {0x30, 0x0A, 0x06, 0x08, 0x2A, 0x86, 0x48, 0x86, 0xF7, 0x0D, 0x01, 0x01, 0x05};
        ByteArrayInputStream bais = new ByteArrayInputStream(validBytes);
        try {
            new CertificateAlgorithmId(bais);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid InputStream", e);
        }
        // Test with input that triggers IOException (simulate EOF)
        byte[] invalidBytes = {0x30, 0x01}; // incomplete DER
        ByteArrayInputStream invalidBais = new ByteArrayInputStream(invalidBytes);
        try {
            new CertificateAlgorithmId(invalidBais);
            throw new AssertionError("Expected IOException not thrown for invalid InputStream");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
