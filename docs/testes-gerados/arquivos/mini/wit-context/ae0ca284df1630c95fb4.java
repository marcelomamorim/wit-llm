/*
 * @test
 * @summary Test deterministic behavior of NameConstraintsExtension.encode(OutputStream)
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

        // extensionValue is null initially, so encode() should set extensionId and critical, then encodeThis()
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when extensionValue is null");
        }
    }

    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        // Create NameConstraintsExtension with non-null permitted and excluded subtrees
        // Use empty GeneralSubtrees instances (size 0) to avoid complexity
        GeneralSubtrees permitted = new GeneralSubtrees();
        GeneralSubtrees excluded = new GeneralSubtrees();
        NameConstraintsExtension ext = new NameConstraintsExtension(permitted, excluded);

        // Manually set extensionValue to non-null to simulate encoded state
        // Since extensionValue is private and no setter, rely on constructor behavior
        // The constructor with GeneralSubtrees sets extensionValue via encodeThis internally

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when extensionValue is non-null");
        }
    }
}
