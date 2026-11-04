/*
 * @test
 * @summary Test sun.security.pkcs.PKCS9Attributes.encode(byte, OutputStream) for correct encoding and bounds
 * @run main PKCS9AttributesWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.pkcs.PKCS9Attributes;
import sun.security.util.DerInputStream;
import sun.security.util.ObjectIdentifier;

public class PKCS9AttributesWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeBasic();
        testEncodeEmptyDerEncoding();
    }

    private static void testEncodeBasic() throws IOException {
        // Prepare a minimal valid DER encoding for PKCS9Attributes
        // Using a dummy DER encoding with tag 0x31 (SetOf) and length 3 bytes
        byte[] derEncoded = new byte[] {0x31, 0x01, 0x00};
        DerInputStream derIn = new DerInputStream(derEncoded);

        // Construct PKCS9Attributes with null permittedAttributes to accept all
        PKCS9Attributes attrs = new PKCS9Attributes((ObjectIdentifier[]) null, derIn);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte tag = 0x31; // typical tag for SetOf
        attrs.encode(tag, baos);

        byte[] encoded = baos.toByteArray();

        // The first byte should be the tag
        if (encoded.length == 0 || encoded[0] != tag) {
            throw new RuntimeException("Encoded output does not start with the tag byte");
        }

        // The rest of the bytes should match derEncoding from index 1 onward
        for (int i = 1; i < encoded.length; i++) {
            if (encoded[i] != derEncoded[i]) {
                throw new RuntimeException("Encoded output differs from derEncoding at index " + i);
            }
        }
    }

    private static void testEncodeEmptyDerEncoding() throws IOException {
        // Construct PKCS9Attributes with a DER encoding of length 1 to test bounds
        byte[] derEncoded = new byte[] {0x31}; // length 1
        DerInputStream derIn = new DerInputStream(derEncoded);

        PKCS9Attributes attrs = new PKCS9Attributes((ObjectIdentifier[]) null, derIn);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte tag = 0x31;

        try {
            attrs.encode(tag, baos);
            // If no exception, check if derEncoding length is 1, then encode tries to write derEncoding from 1 to length-1 = 0 bytes
            // So no bytes should be written after tag
            byte[] encoded = baos.toByteArray();
            if (encoded.length != 1) {
                throw new RuntimeException("Encoded output length unexpected for derEncoding length 1");
            }
        } catch (IndexOutOfBoundsException | IOException e) {
            // According to WIT, an outOfBounds exception might be thrown if derEncoding length is too small
            // This is acceptable behavior to protect against invalid derEncoding
        }
    }
}