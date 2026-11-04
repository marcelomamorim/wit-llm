/*
 * @test
 * @summary Test ContentInfo constructor with byte[] input
 * @run main ContentInfoWitupTest
 */

import sun.security.pkcs.ContentInfo;
import sun.security.util.DerValue;
import sun.security.pkcs.ContentInfo;

public class ContentInfoWitupTest {
    public static void main(String[] args) throws Exception {
        // Test with non-empty byte array
        byte[] data = {1, 2, 3, 4, 5};
        ContentInfo ci = new ContentInfo(data);
        if (ci == null) {
            throw new RuntimeException("ContentInfo instance is null");
        }
        if (!ci.contentType.equals(ContentInfo.DATA_OID)) {
            throw new RuntimeException("contentType is not DATA_OID");
        }
        if (ci.content == null) {
            throw new RuntimeException("content is null");
        }
        if (ci.content.getTag() != DerValue.tag_OctetString) {
            throw new RuntimeException("content tag is not OctetString");
        }

        // Test with empty byte array
        byte[] empty = new byte[0];
        ContentInfo ciEmpty = new ContentInfo(empty);
        if (ciEmpty == null) {
            throw new RuntimeException("ContentInfo instance is null for empty input");
        }
        if (!ciEmpty.contentType.equals(ContentInfo.DATA_OID)) {
            throw new RuntimeException("contentType is not DATA_OID for empty input");
        }
        if (ciEmpty.content == null) {
            throw new RuntimeException("content is null for empty input");
        }
        if (ciEmpty.content.getTag() != DerValue.tag_OctetString) {
            throw new RuntimeException("content tag is not OctetString for empty input");
        }
    }
}
