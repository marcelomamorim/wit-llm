/*
 * @test
 * @summary Test sun.security.x509.NameConstraintsExtension.encode(OutputStream) method
 * @run main NameConstraintsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.NameConstraintsExtension;
import sun.security.x509.GeneralSubtrees;

public class NameConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create NameConstraintsExtension with null permitted and excluded subtrees
        NameConstraintsExtension ext = new NameConstraintsExtension(null, null);

        // extensionValue is null initially
        // encode should set extensionId and critical, call encodeThis, then write encoded bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }
    }

    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        // Create NameConstraintsExtension with empty GeneralSubtrees to force non-null extensionValue
        GeneralSubtrees permitted = new GeneralSubtrees();
        GeneralSubtrees excluded = new GeneralSubtrees();
        NameConstraintsExtension ext = new NameConstraintsExtension(permitted, excluded);

        // Manually set extensionValue to non-null to simulate already encoded extension
        ext.extensionValue = new byte[] {1, 2, 3};

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when extensionValue is non-null");
        }
    }
}