/*
 * @test
 * @summary Tests for DistributionPointName constructors and exception paths
 * @run main DistributionPointNameWitupTest
 */

import sun.security.util.DerValue;
import sun.security.x509.DistributionPointName;
import sun.security.x509.GeneralNames;
import sun.security.x509.RDN;

import java.io.IOException;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidFullName();
        testConstructorWithValidRelativeName();
        testConstructorWithInvalidEncoding();
        testConstructorWithInvalidTag();
        testConstructorWithEmptyBuffer();
    }

    static void testConstructorWithValidFullName() {
        try {
            // Create a valid GeneralNames object
            GeneralNames gn = new GeneralNames();
            // Create DerValue with context-specific tag 0 (fullName)
            DerValue dv = new DerValue(DerValue.tag_Sequence, new byte[] {0xA0, 0x00});
            // Construct DistributionPointName
            DistributionPointName dpn = new DistributionPointName(gn);
            assert dpn != null : "DistributionPointName with fullName should not be null";
        } catch (Exception e) {
            throw new RuntimeException("Failed in testConstructorWithValidFullName", e);
        }
    }

    static void testConstructorWithValidRelativeName() {
        try {
            // Create a valid RDN object
            RDN rdn = new RDN();
            // Create DerValue with context-specific tag 1 (relativeName)
            DerValue dv = new DerValue(DerValue.tag_Sequence, new byte[] {0xA1, 0x00});
            // Construct DistributionPointName
            DistributionPointName dpn = new DistributionPointName(rdn);
            assert dpn != null : "DistributionPointName with relativeName should not be null";
        } catch (Exception e) {
            throw new RuntimeException("Failed in testConstructorWithValidRelativeName", e);
        }
    }

    static void testConstructorWithInvalidEncoding() {
        try {
            // Create DerValue with invalid encoding (neither fullName nor relativeName)
            DerValue dv = new DerValue(0xFF, new byte[] {0x00});
            new DistributionPointName(dv);
            throw new AssertionError("Expected IOException for invalid encoding") ;
        } catch (IOException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testConstructorWithInvalidEncoding", e);
        }
    }

    static void testConstructorWithInvalidTag() {
        try {
            // Create DerValue with invalid tag for fullName
            DerValue dv = new DerValue(DerValue.tag_Sequence, new byte[] {0xA2, 0x00});
            new DistributionPointName(dv);
            throw new AssertionError("Expected IOException for invalid tag") ;
        } catch (IOException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testConstructorWithInvalidTag", e);
        }
    }

    static void testConstructorWithEmptyBuffer() {
        try {
            // Create DerValue with empty buffer
            DerValue dv = new DerValue(DerValue.tag_Sequence, new byte[] {});
            new DistributionPointName(dv);
            throw new AssertionError("Expected IOException for empty buffer") ;
        } catch (IOException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testConstructorWithEmptyBuffer", e);
        }
    }
}
