/*
 * @test
 * @summary Test sun.security.x509.Extension.encode(OutputStream) method
 * @run main ExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.Extension;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class ExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeNullOutputStream();
        testEncodeNonCriticalExtension();
        testEncodeCriticalExtension();
    }

    private static void testEncodeNullOutputStream() {
        try {
            Extension ext = new Extension();
            ext.encode(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testEncodeNonCriticalExtension() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3, 4});
        byte[] value = new byte[] {0x01, 0x02, 0x03};
        Extension ext = new Extension(oid, false, value);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }

        // Basic sanity check: encoded data should start with DER SEQUENCE tag (0x30)
        if (encoded[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("Encoded data does not start with DER SEQUENCE tag");
        }
    }

    private static void testEncodeCriticalExtension() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {2, 5, 29, 15});
        byte[] value = new byte[] {0x05, 0x00}; // DER NULL
        Extension ext = new Extension(oid, true, value);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }

        // Basic sanity check: encoded data should start with DER SEQUENCE tag (0x30)
        if (encoded[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("Encoded data does not start with DER SEQUENCE tag");
        }

        // Check that the critical flag is encoded (boolean tag 0x01) somewhere inside
        boolean foundBooleanTag = false;
        for (int i = 0; i < encoded.length; i++) {
            if (encoded[i] == DerValue.tag_Boolean) {
                foundBooleanTag = true;
                break;
            }
        }
        if (!foundBooleanTag) {
            throw new RuntimeException("Critical boolean flag not encoded in output");
        }
    }
}
