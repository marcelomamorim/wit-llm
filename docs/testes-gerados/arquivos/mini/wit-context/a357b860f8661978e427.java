/*
 * @test
 * @summary Test IssuingDistributionPointExtension.get(String) method behavior
 * @run main IssuingDistributionPointExtensionWitupTest
 */

import sun.security.x509.IssuingDistributionPointExtension;
import sun.security.x509.DistributionPointName;
import sun.security.x509.ReasonFlags;

import java.io.IOException;

public class IssuingDistributionPointExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create dummy DistributionPointName and ReasonFlags for constructor
        DistributionPointName dpName = null;
        ReasonFlags reasons = null;

        // Construct with all booleans false
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(
                dpName, reasons, false, false, false, false);

        // Test known attribute names return expected types or values
        Object val;

        val = ext.get("point");
        if (val != null) {
            throw new RuntimeException("Expected null for point when distributionPoint is null");
        }

        val = ext.get("indirect_crl");
        if (!(val instanceof Boolean) || ((Boolean) val).booleanValue() != false) {
            throw new RuntimeException("Expected Boolean false for indirect_crl");
        }

        val = ext.get("reasons");
        if (val != null) {
            throw new RuntimeException("Expected null for reasons when revocationReasons is null");
        }

        val = ext.get("only_user_certs");
        if (!(val instanceof Boolean) || ((Boolean) val).booleanValue() != false) {
            throw new RuntimeException("Expected Boolean false for only_user_certs");
        }

        val = ext.get("only_ca_certs");
        if (!(val instanceof Boolean) || ((Boolean) val).booleanValue() != false) {
            throw new RuntimeException("Expected Boolean false for only_ca_certs");
        }

        val = ext.get("only_attribute_certs");
        if (!(val instanceof Boolean) || ((Boolean) val).booleanValue() != false) {
            throw new RuntimeException("Expected Boolean false for only_attribute_certs");
        }

        // Test that get throws IOException for unrecognized attribute name
        boolean threw = false;
        try {
            ext.get("unknown_attribute");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (!msg.contains("unknown_attribute") || !msg.contains("IssuingDistributionPointExtension")) {
                throw new RuntimeException("IOException message does not contain expected text");
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException for unknown attribute name");
        }

        // Test case-insensitivity of recognized names
        val = ext.get("InDiReCt_CrL");
        if (!(val instanceof Boolean) || ((Boolean) val).booleanValue() != false) {
            throw new RuntimeException("Expected Boolean false for indirect_crl with mixed case");
        }

        System.out.println("All tests passed.");
    }
}
