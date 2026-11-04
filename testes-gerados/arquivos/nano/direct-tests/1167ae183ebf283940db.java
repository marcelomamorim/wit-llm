/* @test    @summary Test encoding of SubjectInfoAccessExtension with a simple access description    @run main SubjectInfoAccessExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import sun.security.x509.SubjectInfoAccessExtension;
import sun.security.x509.AccessDescription;
import sun.security.util.DerOutputStream;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithSingleAccessDescription();
        testEncodeWithMultipleAccessDescriptions();
        testEncodeWithNullExtensionValue();
    }

    static void testEncodeWithSingleAccessDescription() throws IOException {
        List<AccessDescription> descriptions = new ArrayList<>();
        AccessDescription ad = new AccessDescription(
                new sun.security.x509.ObjectIdentifier("1.3.6.1.5.5.7.48.1"),
                new sun.security.x509.GeneralName(
                        sun.security.x509.GeneralName.uniformResourceIdentifier,
                        "http://example.com"));
        descriptions.add(ad);
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(descriptions);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        // Basic check: encoded array should not be empty
        if (encoded.length == 0) {
            throw new AssertionError("Encoding resulted in empty byte array");
        }
        System.out.println("testEncodeWithSingleAccessDescription passed.");
    }

    static void testEncodeWithMultipleAccessDescriptions() throws IOException {
        List<AccessDescription> descriptions = new ArrayList<>();
        AccessDescription ad1 = new AccessDescription(
                new sun.security.x509.ObjectIdentifier("1.3.6.1.5.5.7.48.2"),
                new sun.security.x509.GeneralName(
                        sun.security.x509.GeneralName.uniformResourceIdentifier,
                        "https://example.org"));
        AccessDescription ad2 = new AccessDescription(
                new sun.security.x509.ObjectIdentifier("1.3.6.1.5.5.7.48.3"),
                new sun.security.x509.GeneralName(
                        sun.security.x509.GeneralName.uniformResourceIdentifier,
                        "ftp://example.net"));
        descriptions.add(ad1);
        descriptions.add(ad2);
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(descriptions);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded.length == 0) {
            throw new AssertionError("Encoding resulted in empty byte array");
        }
        System.out.println("testEncodeWithMultipleAccessDescriptions passed.");
    }

    static void testEncodeWithNullExtensionValue() throws IOException {
        List<AccessDescription> descriptions = new ArrayList<>();
        SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(descriptions);
        // Force extensionValue to null to test encodeThis() call
        ext.extensionValue = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded.length == 0) {
            throw new AssertionError("Encoding with null extensionValue resulted in empty byte array");
        }
        System.out.println("testEncodeWithNullExtensionValue passed.");
    }
}
