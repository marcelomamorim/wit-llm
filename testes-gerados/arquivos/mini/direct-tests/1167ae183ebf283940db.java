/*
 * @test
 * @summary Test SubjectInfoAccessExtension.encode(OutputStream) behavior
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sun.security.x509.AccessDescription;
import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithNonNullExtensionValue();
        testEncodeWithNullExtensionValue();
    }

    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        // Create a SubjectInfoAccessExtension with a non-null extensionValue
        List<AccessDescription> ads = new ArrayList<>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(ads);

        // Manually set extensionValue to non-null to test early path
        ext.extensionValue = new byte[] {1, 2, 3};

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty when extensionValue is set");
        }
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create a SubjectInfoAccessExtension with null extensionValue to test encodeThis path
        List<AccessDescription> ads = new ArrayList<>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(ads);

        // Ensure extensionValue is null
        ext.extensionValue = null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty when extensionValue is null");
        }
    }
}