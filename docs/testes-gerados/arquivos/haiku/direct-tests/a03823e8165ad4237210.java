/*
 * @test
 * @summary Basic checks for the ContentInfo(byte[]) constructor
 * @modules java.base/sun.security.pkcs java.base/sun.security.util
 * @run main ContentInfoWitupTest
 */

import sun.security.pkcs.ContentInfo;
import sun.security.util.ObjectIdentifier;

public class ContentInfoWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] payload = {0x01, 0x02, 0x03};
        ContentInfo ci = new ContentInfo(payload);
        if (ci == null) {
            throw new RuntimeException("constructor returned null");
        }

        ObjectIdentifier expected = new ObjectIdentifier("1.2.840.113549.1.7.1");
        if (!expected.equals(ContentInfo.DATA_OID)) {
            throw new RuntimeException("DATA_OID mismatch: " + ContentInfo.DATA_OID);
        }

        ContentInfo empty = new ContentInfo(new byte[0]);
        if (empty == null) {
            throw new RuntimeException("constructor returned null for empty input");
        }
    }
}
