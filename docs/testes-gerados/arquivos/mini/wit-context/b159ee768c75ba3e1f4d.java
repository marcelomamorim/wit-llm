/*
 * @test
 * @summary Test sun.security.x509.URIName.nameConstraint(DerValue) for valid and invalid inputs
 * @run main URINameWitupTest
 */

import sun.security.x509.URIName;
import sun.security.util.DerValue;
import java.io.IOException;

public class URINameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidNameConstraintWithoutScheme();
        testNameConstraintWithSchemeThrowsIOException();
        testNameConstraintWithInvalidTagThrowsIOException();
    }

    private static void testValidNameConstraintWithoutScheme() throws Exception {
        // DerValue with tag 22 (IA5String) and value ".example.com" (no scheme)
        DerValue derValue = new DerValue(DerValue.tag_IA5String, ".example.com".getBytes("ASCII"));
        URIName uriName = URIName.nameConstraint(derValue);
        if (uriName == null) {
            throw new RuntimeException("Expected non-null URIName for valid input");
        }
    }

    private static void testNameConstraintWithSchemeThrowsIOException() throws Exception {
        // DerValue with tag 22 and value "http://example.com" (has scheme)
        DerValue derValue = new DerValue(DerValue.tag_IA5String, "http://example.com".getBytes("ASCII"));
        try {
            URIName.nameConstraint(derValue);
            throw new RuntimeException("Expected IOException for URI with scheme");
        } catch (IOException e) {
            // Expected exception
            String msg = e.getMessage();
            if (!msg.contains("invalid URI name constraint (should not include scheme)")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testNameConstraintWithInvalidTagThrowsIOException() throws Exception {
        // DerValue with invalid tag (not 22) should cause IOException from getIA5String
        // Create DerValue with tag 20 (not IA5String)
        DerValue derValue = new DerValue((byte)20, "ignored".getBytes("ASCII"));
        try {
            URIName.nameConstraint(derValue);
            throw new RuntimeException("Expected IOException for DerValue with invalid tag");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
