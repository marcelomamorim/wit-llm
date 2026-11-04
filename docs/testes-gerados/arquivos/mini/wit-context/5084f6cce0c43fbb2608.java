/*
 * @test
 * @summary Test sun.security.pkcs.ContentInfo.encode(DerOutputStream) for normal and edge cases
 * @run main ContentInfoWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import sun.security.pkcs.ContentInfo;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class ContentInfoWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithContent();
        testEncodeWithoutContent();
    }

    private static void testEncodeWithContent() throws IOException {
        // Prepare a dummy content DerValue
        byte[] dummyData = {0x01, 0x02, 0x03};
        DerValue content = new DerValue(dummyData);

        // Use a known OID (DATA_OID) for contentType
        ContentInfo ci = new ContentInfo(ContentInfo.DATA_OID, content);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream out = new DerOutputStream(baos);

        ci.encode(out);

        byte[] encoded = baos.toByteArray();

        // Basic sanity checks on encoded output
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty");
        }

        // The first byte should be the DER sequence tag (0x30)
        if (encoded[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("Encoded output does not start with DER sequence tag");
        }
    }

    private static void testEncodeWithoutContent() throws IOException {
        // Create ContentInfo with null content
        ContentInfo ci = new ContentInfo(ContentInfo.DATA_OID, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream out = new DerOutputStream(baos);

        ci.encode(out);

        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when content is null");
        }

        if (encoded[0] != DerValue.tag_Sequence) {
            throw new RuntimeException("Encoded output does not start with DER sequence tag when content is null");
        }
    }
}
