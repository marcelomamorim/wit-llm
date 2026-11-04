/*
 * @test
 * @summary Test sun.security.pkcs.PKCS9Attributes.encode(byte, OutputStream) method
 * @run main PKCS9AttributesWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.pkcs.PKCS9Attributes;
sun.security.util.DerInputStream;
sun.security.util.ObjectIdentifier;

public class PKCS9AttributesWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeProducesExpectedOutput();
    }

    private static void testEncodeProducesExpectedOutput() throws IOException {
        // Prepare a minimal DER encoding for PKCS9Attributes
        // Use empty attribute set encoding: DER encoding of SET OF with zero elements is 0x31 0x00
        byte[] derEncodedEmptySet = new byte[] {0x31, 0x00};

        // Create DerInputStream from the minimal encoding
        DerInputStream derIn = new DerInputStream(derEncodedEmptySet);

        // Construct PKCS9Attributes with null permittedAttributes to accept all
        PKCS9Attributes attrs = new PKCS9Attributes((ObjectIdentifier[])null, derIn);

        // Prepare output stream to capture encode output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Use tag 0x31 (SET OF) as typical tag for attribute set
        byte tag = 0x31;

        // Call encode
        attrs.encode(tag, baos);

        byte[] encoded = baos.toByteArray();

        // The encode method writes the tag byte, then derEncoding from index 1 onward
        // derEncoding is expected to be the DER encoding of the attribute set
        // So encoded[0] == tag
        if (encoded.length != derEncodedEmptySet.length) {
            throw new RuntimeException("Encoded output length mismatch: expected " + derEncodedEmptySet.length + " but got " + encoded.length);
        }
        if (encoded[0] != tag) {
            throw new RuntimeException("Encoded output first byte (tag) mismatch: expected " + tag + " but got " + encoded[0]);
        }
        // The rest of encoded should equal derEncoding from index 1 onward
        for (int i = 1; i < encoded.length; i++) {
            if (encoded[i] != derEncodedEmptySet[i]) {
                throw new RuntimeException("Encoded output byte mismatch at index " + i + ": expected " + derEncodedEmptySet[i] + " but got " + encoded[i]);
            }
        }
    }
}
