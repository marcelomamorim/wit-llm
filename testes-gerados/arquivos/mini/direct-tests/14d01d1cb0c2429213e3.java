/*
 * @test
 * @summary Test AuthorityInfoAccessExtension.encode(OutputStream) behavior
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.AccessDescription;
import sun.security.x509.PKIXExtensions;
import sun.security.util.DerOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorityInfoAccessExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithNonNullExtensionValue();
        testEncodeWithNullExtensionValue();
    }

    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        // Prepare a minimal AccessDescription list
        List<AccessDescription> ads = new ArrayList<>();
        // AccessDescription requires an OID and a GeneralName, but since we have no context,
        // we create a dummy AccessDescription with minimal valid data.
        // However, AccessDescription constructor is not provided in context,
        // so we test with empty list to avoid reflection or unknown constructors.

        // Create AuthorityInfoAccessExtension with empty list
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(ads);

        // extensionValue should be non-null after construction
        if (ext.extensionValue == null) {
            throw new RuntimeException("extensionValue should not be null after construction");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty");
        }

        // Check that extensionId is PKIXExtensions.AuthInfoAccess_Id
        if (!ext.extensionId.equals(PKIXExtensions.AuthInfoAccess_Id)) {
            throw new RuntimeException("extensionId should be AuthInfoAccess_Id");
        }

        // Check that critical is false
        if (ext.critical != false) {
            throw new RuntimeException("critical should be false");
        }
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create AuthorityInfoAccessExtension with empty list
        List<AccessDescription> ads = new ArrayList<>();
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(ads);

        // Manually set extensionValue to null to test encode() path
        ext.extensionValue = null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty when extensionValue is null");
        }

        // Check that extensionId is PKIXExtensions.AuthInfoAccess_Id
        if (!ext.extensionId.equals(PKIXExtensions.AuthInfoAccess_Id)) {
            throw new RuntimeException("extensionId should be AuthInfoAccess_Id after encode");
        }

        // Check that critical is false
        if (ext.critical != false) {
            throw new RuntimeException("critical should be false after encode");
        }
    }
}
