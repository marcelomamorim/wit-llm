/*
 * @test
 * @summary Test CertificateAlgorithmId constructor with DerInputStream for IOException paths
 * @run main CertificateAlgorithmIdWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.x509.CertificateAlgorithmId;

public class CertificateAlgorithmIdWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidDerInputStream();
        testConstructorWithEmptyDerInputStream();
        testConstructorWithMalformedDerInputStream();
    }

    private static void testConstructorWithValidDerInputStream() throws Exception {
        // A minimal valid DER encoding for an AlgorithmId (e.g., OID 1.2.840.113549.1.1.5 - sha1WithRSAEncryption)
        // DER encoding: 30 0B 06 09 2A 86 48 86 F7 0D 01 01 05 05 00
        // But to keep test simple and deterministic, use a known valid encoding from AlgorithmId tests or minimal valid DER
        byte[] validDer = new byte[] {
            0x30, 0x0B, // SEQUENCE length 11
            0x06, 0x09, // OID tag and length 9
            0x2A, (byte)0x86, 0x48, (byte)0x86, (byte)0xF7, 0x0D, 0x01, 0x01, 0x05,
            0x05, 0x00 // NULL
        };
        DerInputStream derIn = new DerInputStream(validDer);
        CertificateAlgorithmId certAlgId = new CertificateAlgorithmId(derIn);
        if (certAlgId == null) {
            throw new AssertionError("Constructor returned null for valid DER input");
        }
        String str = certAlgId.toString();
        if (str.isEmpty()) {
            throw new AssertionError("toString() returned empty string for valid CertificateAlgorithmId");
        }
    }

    private static void testConstructorWithEmptyDerInputStream() {
        // Empty input stream should cause IOException when getDerValue() is called
        byte[] empty = new byte[0];
        DerInputStream derIn = new DerInputStream(empty);
        try {
            new CertificateAlgorithmId(derIn);
            throw new AssertionError("Expected IOException not thrown for empty DerInputStream");
        } catch (IOException expected) {
            // Expected exception
        }
    }

    private static void testConstructorWithMalformedDerInputStream() {
        // Malformed DER: invalid tag or length causing IOException in getDerValue or AlgorithmId.parse
        // For example, truncated SEQUENCE
        byte[] malformed = new byte[] {0x30, 0x10, 0x06}; // SEQUENCE length 16 but incomplete
        DerInputStream derIn = new DerInputStream(malformed);
        try {
            new CertificateAlgorithmId(derIn);
            throw new AssertionError("Expected IOException not thrown for malformed DerInputStream");
        } catch (IOException expected) {
            // Expected exception
        }
    }
}
