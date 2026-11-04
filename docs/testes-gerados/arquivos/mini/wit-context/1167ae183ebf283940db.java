/*
 * @test
 * @summary Test SubjectInfoAccessExtension.encode(OutputStream) for normal and edge cases
 * @run main SubjectInfoAccessExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import sun.security.x509.AccessDescription;
import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create a SubjectInfoAccessExtension with empty AccessDescription list
        List<AccessDescription> emptyList = new ArrayList<>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(emptyList);

        // extensionValue is initially null, so encode() should set extensionId, critical and encodeThis()
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        // The encoded output should not be empty
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded output is empty when extensionValue was null");
        }
    }

    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        // Create a SubjectInfoAccessExtension with empty AccessDescription list
        List<AccessDescription> emptyList = new ArrayList<>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(emptyList);

        // Force extensionValue to non-null by calling encodeThis() explicitly
        ext.encodeThis();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        // The encoded output should not be empty
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded output is empty when extensionValue was non-null");
        }
    }
}
