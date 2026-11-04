/*
 * @test
 * @summary Test sun.security.pkcs.ContentInfo.encode(DerOutputStream) method
 * @run main ContentInfoWitupTest
 */

import sun.security.pkcs.ContentInfo;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;

public class ContentInfoWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithContent();
        testEncodeWithoutContent();
    }

    private static void testEncodeWithContent() throws IOException {
        // Prepare contentType OID
        int[] oidInts = {1, 2, 840, 113549, 1, 7, 1}; // DATA_OID
        ObjectIdentifier contentType = ObjectIdentifier.newInternal(oidInts);

        // Prepare content DerValue with simple data
        byte[] contentBytes = {0x01, 0x02, 0x03};
        DerValue content = new DerValue(DerValue.tag_OctetString, contentBytes);

        ContentInfo ci = new ContentInfo(contentType, content);

        DerOutputStream out = new DerOutputStream();
        ci.encode(out);

        byte[] encoded = out.toByteArray();

        // Basic checks on encoding
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }

        // The encoding should start with a SEQUENCE tag (0x30)
        if (encoded[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("Encoded output does not start with SEQUENCE tag");
        }
    }

    private static void testEncodeWithoutContent() throws IOException {
        // Prepare contentType OID
        int[] oidInts = {1, 2, 840, 113549, 1, 7}; // PKCS7_OID
        ObjectIdentifier contentType = ObjectIdentifier.newInternal(oidInts);

        // Content is null
        ContentInfo ci = new ContentInfo(contentType, null);

        DerOutputStream out = new DerOutputStream();
        ci.encode(out);

        byte[] encoded = out.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when content is null");
        }

        // The encoding should start with a SEQUENCE tag (0x30)
        if (encoded[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("Encoded output does not start with SEQUENCE tag when content is null");
        }
    }
}
