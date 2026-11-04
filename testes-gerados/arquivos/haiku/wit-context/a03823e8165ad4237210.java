/*
 * @test
 * @summary Smoke test for the public ContentInfo(byte[]) constructor, which must accept arbitrary byte arrays without throwing
 * @modules java.base/sun.security.pkcs
 * @run main ContentInfoWitupTest
 */

import sun.security.pkcs.ContentInfo;

public class ContentInfoWitupTest {

    public static void main(String[] args) {
        byte[][] inputs = {
            new byte[0],
            new byte[] {0x01},
            new byte[] {0x04, 0x00},
            new byte[] {0x30, 0x03, 0x02, 0x01, 0x05}
        };
        for (byte[] input : inputs) {
            ContentInfo info = new ContentInfo(input);
            if (info == null) {
                throw new RuntimeException("ContentInfo(byte[]) returned null");
            }
        }
    }
}
