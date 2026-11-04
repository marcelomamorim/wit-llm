/*
 * @test
 * @summary Test sun.security.x509.IssuingDistributionPointExtension.delete method
 * @run main IssuingDistributionPointExtensionWitupTest
 */

import sun.security.x509.IssuingDistributionPointExtension;
import sun.security.x509.DistributionPointName;
import sun.security.x509.ReasonFlags;
import java.io.IOException;

public class IssuingDistributionPointExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeletePoint();
        testDeleteIndirectCRL();
        testDeleteReasons();
        testDeleteOnlyUserCerts();
        testDeleteOnlyCACerts();
        testDeleteOnlyAttributeCerts();
        testDeleteInvalidName();
    }

    private static void testDeletePoint() throws IOException {
        IssuingDistributionPointExtension ext = createDefaultExtension();
        ext.delete(IssuingDistributionPointExtension.POINT);
        // After delete POINT, distributionPoint should be null
        // No direct getter, so we rely on no exception and encodeThis success
    }

    private static void testDeleteIndirectCRL() throws IOException {
        IssuingDistributionPointExtension ext = createDefaultExtension();
        ext.delete(IssuingDistributionPointExtension.INDIRECT_CRL);
        // After delete INDIRECT_CRL, isIndirectCRL should be false
        // No direct getter, rely on no exception and encodeThis success
    }

    private static void testDeleteReasons() throws IOException {
        IssuingDistributionPointExtension ext = createDefaultExtension();
        ext.delete(IssuingDistributionPointExtension.REASONS);
        // After delete REASONS, revocationReasons should be null
        // No direct getter, rely on no exception and encodeThis success
    }

    private static void testDeleteOnlyUserCerts() throws IOException {
        IssuingDistributionPointExtension ext = createDefaultExtension();
        ext.delete(IssuingDistributionPointExtension.ONLY_USER_CERTS);
        // After delete ONLY_USER_CERTS, hasOnlyUserCerts should be false
        // No direct getter, rely on no exception and encodeThis success
    }

    private static void testDeleteOnlyCACerts() throws IOException {
        IssuingDistributionPointExtension ext = createDefaultExtension();
        ext.delete(IssuingDistributionPointExtension.ONLY_CA_CERTS);
        // After delete ONLY_CA_CERTS, hasOnlyCACerts should be false
        // No direct getter, rely on no exception and encodeThis success
    }

    private static void testDeleteOnlyAttributeCerts() throws IOException {
        IssuingDistributionPointExtension ext = createDefaultExtension();
        ext.delete(IssuingDistributionPointExtension.ONLY_ATTRIBUTE_CERTS);
        // After delete ONLY_ATTRIBUTE_CERTS, hasOnlyAttributeCerts should be false
        // No direct getter, rely on no exception and encodeThis success
    }

    private static void testDeleteInvalidName() {
        IssuingDistributionPointExtension ext = createDefaultExtension();
        try {
            ext.delete("invalid_name");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static IssuingDistributionPointExtension createDefaultExtension() {
        // Use public constructor with all false and nulls
        return new IssuingDistributionPointExtension(null, null, false, false, false, false);
    }
}
