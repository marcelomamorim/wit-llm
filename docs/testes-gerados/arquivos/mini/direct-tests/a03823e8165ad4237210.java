/*
 * @test
 * @summary Test ContentInfo constructor with byte[] input
 * @run main ContentInfoWitupTest
 */

import sun.security.pkcs.ContentInfo;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;

public class ContentInfoWitupTest {
    public static void main(String[] args) throws Exception {
        // Test with non-empty byte array
        byte[] data = {1, 2, 3, 4, 5};
        ContentInfo ci = new ContentInfo(data);
        if (!ci.contentType.equals(ContentInfo.DATA_OID)) {
            throw new RuntimeException("contentType is not DATA_OID");
        }
        DerValue content = ci.content;
        if (content == null) {
            throw new RuntimeException("content is null");
        }
        if (content.getTag() != DerValue.tag_OctetString) {
            throw new RuntimeException("content tag is not OctetString");
        }
        byte[] contentBytes = content.getOctetString();
        if (contentBytes.length != data.length) {
            throw new RuntimeException("content bytes length mismatch");
        }
        for (int i = 0; i < data.length; i++) {
            if (contentBytes[i] != data[i]) {
                throw new RuntimeException("content bytes differ at index " + i);
            }
        }

        // Test with empty byte array
        byte[] empty = new byte[0];
        ContentInfo ciEmpty = new ContentInfo(empty);
        if (!ciEmpty.contentType.equals(ContentInfo.DATA_OID)) {
            throw new RuntimeException("contentType is not DATA_OID for empty input");
        }
        DerValue contentEmpty = ciEmpty.content;
        if (contentEmpty == null) {
            throw new RuntimeException("content is null for empty input");
        }
        if (contentEmpty.getTag() != DerValue.tag_OctetString) {
            throw new RuntimeException("content tag is not OctetString for empty input");
        }
        byte[] contentEmptyBytes = contentEmpty.getOctetString();
        if (contentEmptyBytes.length != 0) {
            throw new RuntimeException("content bytes length not zero for empty input");
        }

        // Test with null input - expect NullPointerException
        try {
            new ContentInfo(null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
