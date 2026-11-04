/* @test    @summary Test constructor DistributionPointName(DerValue encoding) for DistributionPointName class    @run main DistributionPointNameWitupTest */

import sun.security.util.DerValue;
import sun.security.x509.DistributionPointName;
import java.io.IOException;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) throws IOException {
        testFullNameConstructor();
        testRelativeNameConstructor();
        testInvalidEncoding();
    }

    private static void testFullNameConstructor() throws IOException {
        // Create a DerValue representing a fullName with context-specific tag 0
        byte[] encodedFullName = {
            (byte) 0xa0, // [0] constructed
            0x0e, // length
            0x30, 0x0c, // sequence length
            0x30, 0x0a, // inner sequence length
            0x06, 0x03, 0x55, 0x04, 0x03, // OID for commonName
            0x13, 0x03, 0x66, 0x6f, 0x6f // 'foo'
        };
        DerValue dv = new DerValue(encodedFullName);
        DistributionPointName dpn = new DistributionPointName(dv);
        assert dpn.fullName != null : "fullName should be initialized";
        assert dpn.relativeName == null : "relativeName should be null";
    }

    private static void testRelativeNameConstructor() throws IOException {
        // Create a DerValue representing a relativeName with context-specific tag 1
        byte[] encodedRelName = {
            (byte) 0xa1, // [1] constructed
            0x0a, // length
            0x31, 0x08, // set length
            0x30, 0x06, // sequence length
            0x02, 0x01, 0x01, // attribute type
            0x02, 0x01, 0x02 // attribute value
        };
        DerValue dv = new DerValue(encodedRelName);
        DistributionPointName dpn = new DistributionPointName(dv);
        assert dpn.fullName == null : "fullName should be null";
        assert dpn.relativeName != null : "relativeName should be initialized";
    }

    private static void testInvalidEncoding() {
        byte[] invalidEncoding = { 0x00, 0x01, 0x02 };
        try {
            DerValue dv = new DerValue(invalidEncoding);
            new DistributionPointName(dv);
            assert false : "Expected IOException for invalid encoding";
        } catch (IOException e) {
            // Expected exception
        }
    }
}
