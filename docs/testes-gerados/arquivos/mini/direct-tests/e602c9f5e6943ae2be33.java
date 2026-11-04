/*
 * @test
 * @summary Test SubjectAlternativeNameExtension.encode(OutputStream) behavior
 * @run main SubjectAlternativeNameExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.x509.GeneralNames;
import sun.security.x509.PKIXExtensions;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    // Test encode when extensionValue is null: should set extensionId, critical, and encodeThis
    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create SubjectAlternativeNameExtension with default constructor (extensionValue null)
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();

        // extensionValue is null initially
        if (ext.extensionValue != null) {
            throw new RuntimeException("extensionValue should be null initially");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        // After encode, extensionId should be PKIXExtensions.SubjectAlternativeName_Id
        if (!ext.extensionId.equals(PKIXExtensions.SubjectAlternativeName_Id)) {
            throw new RuntimeException("extensionId not set correctly after encode");
        }

        // critical should be false
        if (ext.critical != false) {
            throw new RuntimeException("critical flag not false after encode");
        }

        // extensionValue should not be null after encode
        if (ext.extensionValue == null) {
            throw new RuntimeException("extensionValue should not be null after encode");
        }

        // encoded output should not be empty
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty");
        }
    }

    // Test encode when extensionValue is already set: should not call encodeThis again
    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        // Create GeneralNames with empty names to produce non-null extensionValue
        GeneralNames names = new GeneralNames();
        SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(Boolean.FALSE, names);

        // extensionValue should be non-null after construction
        if (ext.extensionValue == null) {
            throw new RuntimeException("extensionValue should not be null after construction");
        }

        byte[] beforeEncodeValue = ext.extensionValue.clone();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        // extensionValue should remain unchanged
        if (ext.extensionValue == null) {
            throw new RuntimeException("extensionValue should not be null after encode");
        }

        if (beforeEncodeValue.length != ext.extensionValue.length) {
            throw new RuntimeException("extensionValue changed after encode");
        }

        // encoded output should not be empty
        if (encoded.length == 0) {
            throw new RuntimeException("Encoded output should not be empty");
        }
    }
}
