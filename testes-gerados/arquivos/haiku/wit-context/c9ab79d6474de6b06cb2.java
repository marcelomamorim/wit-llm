/*
 * @test
 * @summary Verify that the ContentInfo(ObjectIdentifier, DerValue) constructor stores its arguments and does not throw for null inputs
 * @modules java.base/sun.security.pkcs
 * @run main ContentInfoWitupTest
 */

import sun.security.pkcs.ContentInfo;

public class ContentInfoWitupTest {

    public static void main(String[] args) {
        ContentInfo nullInfo = new ContentInfo(null, null);
        if (nullInfo.getContentType() != null) {
            throw new RuntimeException("contentType should be null when passed null");
        }
        if (nullInfo.getContent() != null) {
            throw new RuntimeException("content should be null when passed null");
        }
    }
}
