/*
 * @test
 * @summary Test IssuingDistributionPointExtension.encode(OutputStream) for expected behavior and exception paths
 * @run main IssuingDistributionPointExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.IssuingDistributionPointExtension;
import sun.security.x509.DistributionPointName;
import sun.security.x509.ReasonFlags;

public class IssuingDistributionPointExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Construct with all false booleans and null distributionPoint and revocationReasons
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(
                (DistributionPointName) null,
                (ReasonFlags) null,
                false, false, false, false);

        // extensionValue is null initially, so encode should set extensionId and critical, then encodeThis
        // and write to output stream without throwing
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }

    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        // Construct with all false booleans and null distributionPoint and revocationReasons
        IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(
                (DistributionPointName) null,
                (ReasonFlags) null,
                false, false, false, false);

        // Manually set extensionValue to non-null to simulate the condition
        // Since extensionValue is private and no setter, we use the constructor that takes Boolean and Object
        // to create an instance with non-null extensionValue
        // We create a dummy extensionValue as a byte array
        byte[] dummyValue = new byte[] {0x01, 0x02, 0x03};
        IssuingDistributionPointExtension extWithValue = new IssuingDistributionPointExtension(Boolean.FALSE, dummyValue);

        // encode should not throw IOException
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        extWithValue.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty for non-null extensionValue");
        }
    }
}
