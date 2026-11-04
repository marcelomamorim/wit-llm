/*
 * @test
 * @summary Test ExtendedKeyUsageExtension.encode(OutputStream) for normal and edge cases
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import sun.security.util.ObjectIdentifier;
import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.x509.PKIXExtensions;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create ExtendedKeyUsageExtension with null extensionValue by using constructor with Vector
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.1")); // id-kp-serverAuth
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

        // Force extensionValue to null to simulate the condition extensionValue == null
        // This is not possible via public API, so we rely on the constructor behavior:
        // The constructor sets extensionValue to null initially and encode() sets it.
        // So we create a new instance and do not call encodeThis() manually.

        // Use ByteArrayOutputStream to capture output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Call encode, which should set extensionId and critical, then encodeThis internally
        ext.encode(baos);

        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }

        // Check that extensionId is set to PKIXExtensions.ExtendedKeyUsage_Id
        if (!PKIXExtensions.ExtendedKeyUsage_Id.equals(ext.getExtensionId())) {
            throw new RuntimeException("extensionId not set correctly when extensionValue is null");
        }

        // Check that critical is false
        if (ext.isCritical()) {
            throw new RuntimeException("critical flag should be false when extensionValue is null");
        }
    }

    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        // Create ExtendedKeyUsageExtension with usages vector
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.2")); // id-kp-clientAuth
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

        // Call encode once to set extensionValue
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        ext.encode(baos1);

        // Now extensionValue should be non-null
        if (ext.getExtensionValue() == null) {
            throw new RuntimeException("extensionValue should not be null after encode");
        }

        // Call encode again to test path where extensionValue != null
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        ext.encode(baos2);

        byte[] encoded2 = baos2.toByteArray();
        if (encoded2 == null || encoded2.length == 0) {
            throw new RuntimeException("Encoded output is empty on second encode call");
        }
    }
}
