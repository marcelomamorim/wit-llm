/*
 * @test
 * @summary Test DistributionPointName(DerValue) constructor for valid and invalid encodings
 * @run main DistributionPointNameWitupTest
 */

import sun.security.x509.DistributionPointName;
import sun.security.x509.GeneralNames;
import sun.security.x509.RDN;
import sun.security.util.DerValue;

import java.io.IOException;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) throws Exception {
        testValidFullNameEncoding();
        testValidRelativeNameEncoding();
        testInvalidEncodingThrowsIOException();
    }

    private static void testValidFullNameEncoding() throws IOException {
        // Construct a DerValue with context-specific tag 0 (fullName) and constructed flag set
        // The inner encoding must be a SEQUENCE (tag 0x30) for GeneralNames
        byte[] encoded = new byte[] {
            (byte)0xA0, // [0] context-specific constructed
            3,          // length
            0x30,       // SEQUENCE tag
            1,          // length
            0x82        // dummy GeneralName tag (dNSName [2]) with no content
        };
        DerValue der = new DerValue(encoded);
        DistributionPointName dpn = new DistributionPointName(der);
        if (dpn == null) {
            throw new RuntimeException("DistributionPointName instance is null for valid fullName encoding");
        }
    }

    private static void testValidRelativeNameEncoding() throws IOException {
        // Construct a DerValue with context-specific tag 1 (relativeName) and constructed flag set
        // The inner encoding must be a SET (tag 0x31) for RDN
        byte[] encoded = new byte[] {
            (byte)0xA1, // [1] context-specific constructed
            3,          // length
            0x31,       // SET tag
            1,          // length
            0x30        // dummy AttributeTypeAndValue SEQUENCE tag
        };
        DerValue der = new DerValue(encoded);
        DistributionPointName dpn = new DistributionPointName(der);
        if (dpn == null) {
            throw new RuntimeException("DistributionPointName instance is null for valid relativeName encoding");
        }
    }

    private static void testInvalidEncodingThrowsIOException() {
        // Construct a DerValue with invalid tag (not context-specific 0 or 1)
        byte[] encoded = new byte[] {
            0x02, // INTEGER tag (not context-specific)
            1,    // length
            0x01  // value
        };
        try {
            DerValue der = new DerValue(encoded);
            new DistributionPointName(der);
            throw new RuntimeException("Expected IOException not thrown for invalid encoding");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
