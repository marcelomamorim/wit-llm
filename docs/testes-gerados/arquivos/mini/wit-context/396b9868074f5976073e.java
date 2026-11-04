/*
 * @test
 * @summary Test IssuingDistributionPointExtension.delete(String) method behavior
 * @run main IssuingDistributionPointExtensionWitupTest
 */

import sun.security.x509.IssuingDistributionPointExtension;
import sun.security.x509.DistributionPointName;
import sun.security.x509.ReasonFlags;
import java.io.IOException;

public class IssuingDistributionPointExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteValidNames();
        testDeleteInvalidNameThrows();
    }

    private static void testDeleteValidNames() throws IOException {
        DistributionPointName dpName = new DistributionPointName(new byte[] {0x30, 0x00});
        ReasonFlags reasons = new ReasonFlags();
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(
                dpName, reasons, true, true, true, true);

        // Delete POINT
        ext.delete(IssuingDistributionPointExtension.POINT);

        // Delete INDIRECT_CRL
        ext.delete(IssuingDistributionPointExtension.INDIRECT_CRL);

        // Delete REASONS
        ext.delete(IssuingDistributionPointExtension.REASONS);

        // Delete ONLY_USER_CERTS
        ext.delete(IssuingDistributionPointExtension.ONLY_USER_CERTS);

        // Delete ONLY_CA_CERTS
        ext.delete(IssuingDistributionPointExtension.ONLY_CA_CERTS);

        // Delete ONLY_ATTRIBUTE_CERTS
        ext.delete(IssuingDistributionPointExtension.ONLY_ATTRIBUTE_CERTS);
    }

    private static void testDeleteInvalidNameThrows() {
        DistributionPointName dpName = new DistributionPointName(new byte[] {0x30, 0x00});
        ReasonFlags reasons = new ReasonFlags();
        IssuingDistributionPointExtension ext = null;
        try {
            ext = new IssuingDistributionPointExtension(
                    dpName, reasons, true, true, true, true);
        } catch (IOException e) {
            throw new RuntimeException("Setup failed", e);
        }

        try {
            ext.delete("invalid_name");
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            // Expected exception
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
