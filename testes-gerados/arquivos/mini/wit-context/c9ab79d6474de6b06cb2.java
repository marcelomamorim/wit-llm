/*
 * @test
 * @summary Test ContentInfo constructor with ObjectIdentifier and DerValue
 * @run main ContentInfoWitupTest
 */

import sun.security.pkcs.ContentInfo;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;

public class ContentInfoWitupTest {
    public static void main(String[] args) throws Exception {
        // Prepare minimal valid ObjectIdentifier
        int[] oidInts = {1, 2, 840, 113549, 1, 7, 1};
        ObjectIdentifier oid = ObjectIdentifier.newInternal(oidInts);

        // Prepare minimal DerValue with tag 0x04 (OCTET STRING) and empty content
        byte[] derEncoded = {0x04, 0x00};
        DerValue derValue = new DerValue(derEncoded);

        // Construct ContentInfo with valid parameters
        ContentInfo ci = new ContentInfo(oid, derValue);

        // Validate that contentType and content are set as expected
        if (!ci.contentType.equals(oid)) {
            throw new RuntimeException("contentType mismatch");
        }
        if (!ci.content.equals(derValue)) {
            throw new RuntimeException("content mismatch");
        }

        // Test with null content (allowed)
        ContentInfo ciNullContent = new ContentInfo(oid, null);
        if (!ciNullContent.contentType.equals(oid)) {
            throw new RuntimeException("contentType mismatch with null content");
        }
        if (ciNullContent.content != null) {
            throw new RuntimeException("content should be null");
        }

        System.out.println("ContentInfo constructor tests passed.");
    }
}
