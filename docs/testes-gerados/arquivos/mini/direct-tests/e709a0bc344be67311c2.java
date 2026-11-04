/*
 * @test
 * @summary Test encode method of IssuingDistributionPointExtension for correct output and behavior
 * @run main IssuingDistributionPointExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.IssuingDistributionPointExtension;
import sun.security.x509.DistributionPointName;
import sun.security.x509.ReasonFlags;

public class IssuingDistributionPointExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        DistributionPointName dpName = null; // null distribution point
        ReasonFlags reasons = null; // null reasons
        boolean onlyUserCerts = false;
        boolean onlyCACerts = false;
        boolean onlyAttributeCerts = false;
        boolean indirectCRL = false;

        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(
                dpName, reasons, onlyUserCerts, onlyCACerts, onlyAttributeCerts, indirectCRL);

        // extensionValue is null initially
        // encode should set extensionId and critical, then encodeThis and write output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is null or empty");
        }
    }

    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        DistributionPointName dpName = null;
        ReasonFlags reasons = null;
        boolean onlyUserCerts = false;
        boolean onlyCACerts = false;
        boolean onlyAttributeCerts = false;
        boolean indirectCRL = false;

        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(
                dpName, reasons, onlyUserCerts, onlyCACerts, onlyAttributeCerts, indirectCRL);

        // Manually set extensionValue to non-null to test the other branch
        ext.extensionValue = new byte[] {1, 2, 3};

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is null or empty when extensionValue is set");
        }
    }
}