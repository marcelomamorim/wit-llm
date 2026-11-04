/*
 * @test
 * @summary Test AuthorityInfoAccessExtension.delete(String) method behavior
 * @run main AuthorityInfoAccessExtensionWitupTest
 */

import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.AccessDescription;
import sun.security.x509.PKIXExtensions;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteValidNameClearsAccessDescriptions();
        testDeleteInvalidNameThrowsIOException();
    }

    private static void testDeleteValidNameClearsAccessDescriptions() throws Exception {
        // Prepare a list with one dummy AccessDescription
        List<AccessDescription> list = new ArrayList<>();
        // Create dummy AccessDescription with valid OID and GeneralName
        ObjectIdentifier oid = PKIXExtensions.AuthInfoAccess_Id;
        AccessDescription ad = new AccessDescription(oid, null);
        list.add(ad);

        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);

        // Delete with valid name "descriptions" (case insensitive)
        ext.delete("descriptions");

        // After delete, the accessDescriptions list should be empty
        // We check by encoding and decoding to verify internal state
        byte[] encoded = ext.getExtensionValue();
        if (encoded == null) {
            throw new RuntimeException("Encoded extension value is null after delete");
        }

        // The extension should encode to a valid DER sequence with zero elements
        // We decode the extension value (skip OCTET STRING wrapper)
        DerValue der = new DerValue(encoded);
        if (der.tag != DerValue.tag_OctetString) {
            throw new RuntimeException("Encoded extension value is not an OCTET STRING");
        }
        DerValue inner = new DerValue(der.getOctetString());
        if (inner.tag != DerValue.tag_Sequence) {
            throw new RuntimeException("Inner DER value is not a SEQUENCE");
        }
        if (inner.data.available() != 0) {
            throw new RuntimeException("AccessDescriptions sequence is not empty after delete");
        }
    }

    private static void testDeleteInvalidNameThrowsIOException() throws Exception {
        List<AccessDescription> list = new ArrayList<>();
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);

        boolean threw = false;
        try {
            ext.delete("invalidName");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException was not thrown for invalid name");
        }
    }
}
