/*
 * @test
 * @summary Test ExtendedKeyUsageExtension.encode(OutputStream) behavior
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.ExtendedKeyUsageExtension;

public class ExtendedKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws IOException {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create ExtendedKeyUsageExtension with null extensionValue by using constructor with Vector
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.1")); // serverAuth OID
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }

    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        // Create ExtendedKeyUsageExtension with explicit critical and usages
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.2")); // clientAuth OID
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(Boolean.TRUE, usages);

        // Force extensionValue to non-null by calling encodeThis() before encode
        ext.encodeThis();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }
}
