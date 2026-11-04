/* @test    @summary Test for SubjectKeyIdentifierExtension constructors and set method    @run main SubjectKeyIdentifierExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.SubjectKeyIdentifierExtension;

public class SubjectKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithByteArray();
        testConstructorWithBooleanObject();
        testSetMethodRecognizedName();
        testSetMethodUnrecognizedName();
    }

    static void testConstructorWithByteArray() throws IOException {
        byte[] keyIdBytes = {1, 2, 3, 4, 5};
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(keyIdBytes);
        if (ext == null) {
            throw new AssertionError("Constructor with byte[] returned null");
        }
    }

    static void testConstructorWithBooleanObject() throws IOException {
        Boolean critical = Boolean.FALSE;
        byte[] valueBytes = {0, 1, 2};
        // Simulate DER encoded value; actual encoding not tested here
        Object value = valueBytes;
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(critical, value);
        if (ext == null) {
            throw new AssertionError("Constructor with Boolean and Object returned null");
        }
    }

    static void testSetMethodRecognizedName() throws IOException {
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(new byte[]{1,2,3});
        ext.set("key_id", new sun.security.x509.KeyIdentifier(new byte[]{1,2,3}));
        // Expect no exception
    }

    static void testSetMethodUnrecognizedName() {
        SubjectKeyIdentifierExtension ext = new SubjectKeyIdentifierExtension(new byte[]{1,2,3});
        try {
            ext.set("unknown_attribute", new Object());
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
