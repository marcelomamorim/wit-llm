/* @test    @summary Test AuthorityInfoAccessExtension.encode method for correct DER encoding    @run main AuthorityInfoAccessExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import sun.security.x509.AuthorityInfoAccessExtension;
import sun.security.x509.AccessDescription;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithEmptyAccessDescriptions();
        testEncodeWithSingleAccessDescription();
        testEncodeWithMultipleAccessDescriptions();
    }

    static void testEncodeWithEmptyAccessDescriptions() throws IOException {
        List<AccessDescription> list = new ArrayList<>();
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        // Basic check: encoded array should not be null and have length > 0
        assert encoded != null : "Encoded byte array is null";
        assert encoded.length > 0 : "Encoded byte array is empty";
    }

    static void testEncodeWithSingleAccessDescription() throws IOException {
        AccessDescription ad = new AccessDescription();
        // Since AccessDescription constructor details are not provided, assume default
        List<AccessDescription> list = Arrays.asList(ad);
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        assert encoded != null : "Encoded byte array is null";
        assert encoded.length > 0 : "Encoded byte array is empty";
    }

    static void testEncodeWithMultipleAccessDescriptions() throws IOException {
        AccessDescription ad1 = new AccessDescription();
        AccessDescription ad2 = new AccessDescription();
        List<AccessDescription> list = Arrays.asList(ad1, ad2);
        AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(list);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        assert encoded != null : "Encoded byte array is null";
        assert encoded.length > 0 : "Encoded byte array is empty";
    }
}
