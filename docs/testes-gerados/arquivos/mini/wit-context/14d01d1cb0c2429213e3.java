/*
 * @test
 * @summary Test encode(OutputStream) method of AuthorityInfoAccessExtension for correct encoding and exception safety
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import sun.security.x509.AccessDescription;
import sun.security.x509.AuthorityInfoAccessExtension;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithValidAccessDescriptions();
        testEncodeWithNullExtensionValueTriggersEncodeThis();
    }

    private static void testEncodeWithValidAccessDescriptions() throws IOException {
        // Create a minimal AccessDescription list (empty list allowed, no public constructor for AccessDescription details)
        List<AccessDescription> list = new ArrayList<>();
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be null or empty");
        }
    }

    private static void testEncodeWithNullExtensionValueTriggersEncodeThis() throws IOException {
        // Construct AuthorityInfoAccessExtension with null extensionValue by using the (Boolean,Object) constructor
        // Pass null for value to simulate extensionValue == null
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(Boolean.FALSE, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be null or empty when extensionValue is null");
        }
    }
}
