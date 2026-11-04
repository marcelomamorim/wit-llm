/*
 * @test
 * @summary Test sun.security.x509.IssuingDistributionPointExtension.get(String) method
 * @run main IssuingDistributionPointExtensionWitupTest
 */

import sun.security.x509.IssuingDistributionPointExtension;
import sun.security.x509.DistributionPointName;
import sun.security.x509.ReasonFlags;
import java.io.IOException;

public class IssuingDistributionPointExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create an instance with all boolean flags false and null DistributionPointName and ReasonFlags
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(
            (DistributionPointName) null,
            (ReasonFlags) null,
            false, false, false, false
        );

        // Test get with POINT returns null (distributionPoint is null)
        Object point = ext.get(IssuingDistributionPointExtension.POINT);
        if (point != null) {
            throw new RuntimeException("Expected null for POINT but got " + point);
        }

        // Test get with INDIRECT_CRL returns Boolean.FALSE
        Object indirectCrl = ext.get(IssuingDistributionPointExtension.INDIRECT_CRL);
        if (!(indirectCrl instanceof Boolean) || ((Boolean) indirectCrl).booleanValue()) {
            throw new RuntimeException("Expected Boolean.FALSE for INDIRECT_CRL but got " + indirectCrl);
        }

        // Test get with REASONS returns null (revocationReasons is null)
        Object reasons = ext.get(IssuingDistributionPointExtension.REASONS);
        if (reasons != null) {
            throw new RuntimeException("Expected null for REASONS but got " + reasons);
        }

        // Test get with ONLY_USER_CERTS returns Boolean.FALSE
        Object onlyUserCerts = ext.get(IssuingDistributionPointExtension.ONLY_USER_CERTS);
        if (!(onlyUserCerts instanceof Boolean) || ((Boolean) onlyUserCerts).booleanValue()) {
            throw new RuntimeException("Expected Boolean.FALSE for ONLY_USER_CERTS but got " + onlyUserCerts);
        }

        // Test get with ONLY_CA_CERTS returns Boolean.FALSE
        Object onlyCaCerts = ext.get(IssuingDistributionPointExtension.ONLY_CA_CERTS);
        if (!(onlyCaCerts instanceof Boolean) || ((Boolean) onlyCaCerts).booleanValue()) {
            throw new RuntimeException("Expected Boolean.FALSE for ONLY_CA_CERTS but got " + onlyCaCerts);
        }

        // Test get with ONLY_ATTRIBUTE_CERTS returns Boolean.FALSE
        Object onlyAttrCerts = ext.get(IssuingDistributionPointExtension.ONLY_ATTRIBUTE_CERTS);
        if (!(onlyAttrCerts instanceof Boolean) || ((Boolean) onlyAttrCerts).booleanValue()) {
            throw new RuntimeException("Expected Boolean.FALSE for ONLY_ATTRIBUTE_CERTS but got " + onlyAttrCerts);
        }

        // Test get with unknown attribute name throws IOException
        boolean threw = false;
        try {
            ext.get("unknown_attribute_name");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (!msg.contains("unknown_attribute_name") || !msg.contains("IssuingDistributionPointExtension")) {
                throw new RuntimeException("IOException message does not contain expected text: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException for unknown attribute name");
        }
    }
}
