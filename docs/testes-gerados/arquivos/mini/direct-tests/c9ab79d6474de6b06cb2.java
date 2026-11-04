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
        // Create a valid ObjectIdentifier
        int[] oidInts = {1, 2, 840, 113549};
        ObjectIdentifier oid = ObjectIdentifier.newInternal(oidInts);

        // Create a DerValue with a simple byte array
        byte[] derBytes = {0x01, 0x02, 0x03};
        DerValue derValue = new DerValue(derBytes);

        // Construct ContentInfo with valid parameters
        ContentInfo ci = new ContentInfo(oid, derValue);

        // Validate that the contentType and content are set correctly
        if (!ci.contentType.equals(oid)) {
            throw new RuntimeException("contentType not set correctly");
        }
        if (ci.content == null) {
            throw new RuntimeException("content should not be null");
        }

        // Construct ContentInfo with null content
        ContentInfo ciNullContent = new ContentInfo(oid, null);
        if (!ciNullContent.contentType.equals(oid)) {
            throw new RuntimeException("contentType not set correctly when content is null");
        }
        if (ciNullContent.content != null) {
            throw new RuntimeException("content should be null");
        }

        // Construct ContentInfo with null ObjectIdentifier and null content
        ContentInfo ciNullOid = new ContentInfo(null, null);
        if (ciNullOid.contentType != null) {
            throw new RuntimeException("contentType should be null");
        }
        if (ciNullOid.content != null) {
            throw new RuntimeException("content should be null");
        }
    }
}
