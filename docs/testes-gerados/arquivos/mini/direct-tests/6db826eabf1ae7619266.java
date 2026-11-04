/*
 * @test
 * @summary Test DistributionPointName constructor with DerValue encoding
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
        // Create a DerValue with context-specific constructed tag 0 (fullName)
        // Encoding: [0] EXPLICIT SEQUENCE
        byte[] encoded = new byte[] {
            (byte)0xa0, // [0] context-specific constructed
            0x03,       // length 3
            0x30, 0x01, 0x01 // SEQUENCE of length 1 with one element (dummy)
        };
        DerValue der = new DerValue(encoded);
        DistributionPointName dpn = new DistributionPointName(der);
        if (dpn == null) {
            throw new RuntimeException("DistributionPointName instance is null for fullName encoding");
        }
    }

    private static void testValidRelativeNameEncoding() throws IOException {
        // Create a DerValue with context-specific constructed tag 1 (relativeName)
        // Encoding: [1] EXPLICIT SET
        byte[] encoded = new byte[] {
            (byte)0xa1, // [1] context-specific constructed
            0x03,       // length 3
            0x31, 0x01, 0x01 // SET of length 1 with one element (dummy)
        };
        DerValue der = new DerValue(encoded);
        DistributionPointName dpn = new DistributionPointName(der);
        if (dpn == null) {
            throw new RuntimeException("DistributionPointName instance is null for relativeName encoding");
        }
    }

    private static void testInvalidEncodingThrowsIOException() {
        // Create a DerValue with a tag that is not context-specific 0 or 1
        byte[] encoded = new byte[] {
            0x02, // INTEGER tag (not context-specific 0 or 1)
            0x01, // length 1
            0x01  // value
        };
        try {
            DerValue der = new DerValue(encoded);
            new DistributionPointName(der);
            throw new RuntimeException("Expected IOException not thrown for invalid encoding");
        } catch (IOException e) {
            // Expected exception
            String msg = e.getMessage();
            if (!"Invalid encoding for DistributionPointName".equals(msg)) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
