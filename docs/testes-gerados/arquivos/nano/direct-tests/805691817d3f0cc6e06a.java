/* @test    @summary Test encoding of X500Name for sun.security.x509.X500Name    @run main X500NameWitupTest */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.x509.X500Name;

public class X500NameWitupTest {

    public static void main(String[] args) throws IOException {
        testEncodeMethod();
    }

    public static void testEncodeMethod() throws IOException {
        // Create a dummy DerOutputStream to pass to encode
        DerOutputStream outStream = new DerOutputStream();
        // Create a minimal X500Name with no names (empty array)
        X500Name name = new X500Name(new X500Name.RDN[0]);
        // Call encode method
        name.encode(outStream);
        // Verify that the output is a sequence tag followed by empty content
        byte[] encodedBytes = outStream.toByteArray();
        // Sequence tag in DER is 0x30
        boolean isSequenceTag = (encodedBytes.length > 0) && (encodedBytes[0] == 0x30);
        // For empty RDN array, the encoded output should be just the sequence tag with no content
        if (isSequenceTag && encodedBytes.length == 2) {
            System.out.println("encode() test passed for empty RDN array.");
        } else {
            System.out.println("encode() test failed for empty RDN array.");
        }
    }
}
