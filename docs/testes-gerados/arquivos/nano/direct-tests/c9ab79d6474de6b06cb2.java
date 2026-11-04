/* @test    @summary Test constructor ContentInfo(ObjectIdentifier, DerValue) for basic instantiation and field assignment.    @run main ContentInfoWitupTest */

import sun.security.pkcs.ContentInfo;
import sun.security.util.ObjectIdentifier;
import sun.security.util.DerValue;

public class ContentInfoWitupTest {

    public static void main(String[] args) {
        testConstructorAssignsFields();
    }

    private static void testConstructorAssignsFields() {
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 840, 113549, 1, 7, 1});
        byte[] contentBytes = {0x01, 0x02, 0x03};
        DerValue derContent = new DerValue(contentBytes);
        ContentInfo ci = new ContentInfo(oid, derContent);
        assert ci != null : "ContentInfo instance should not be null";
        assert ci.contentType.equals(oid) : "ContentType should match the provided ObjectIdentifier";
        assert ci.content.equals(derContent) : "Content should match the provided DerValue";
    }
}
