/*
 * @test
 * @summary Verify ContentInfo.encode output for null content, explicit content and round-trip from DER bytes
 * @modules java.base/sun.security.pkcs java.base/sun.security.util
 * @run main ContentInfoWitupTest
 */

import java.io.IOException;
import java.util.Arrays;
import sun.security.pkcs.ContentInfo;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class ContentInfoWitupTest {

    public static void main(String[] args) throws IOException {
        testEncodeWithoutContent();
        testEncodeWithContent();
        testRoundTripFromBytes();
    }

    static void testEncodeWithoutContent() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3");
        ContentInfo ci = new ContentInfo(oid, null);
        DerOutputStream out = new DerOutputStream();
        ci.encode(out);
        byte[] expected = {0x30, 0x04, 0x06, 0x02, 0x2A, 0x03};
        check(Arrays.equals(out.toByteArray(), expected),
              "encode without content mismatch");
    }

    static void testEncodeWithContent() throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3");
        DerValue content = new DerValue(new byte[]{0x04, 0x01, 0x41});
        ContentInfo ci = new ContentInfo(oid, content);
        DerOutputStream out = new DerOutputStream();
        ci.encode(out);
        byte[] expected = {0x30, 0x09, 0x06, 0x02, 0x2A, 0x03,
                           (byte) 0xA0, 0x03, 0x04, 0x01, 0x41};
        check(Arrays.equals(out.toByteArray(), expected),
              "encode with content mismatch");
    }

    static void testRoundTripFromBytes() throws IOException {
        byte[] enc = {0x30, 0x09, 0x06, 0x02, 0x2A, 0x03,
                      (byte) 0xA0, 0x03, 0x04, 0x01, 0x41};
        ContentInfo ci = new ContentInfo(enc);
        DerOutputStream out = new DerOutputStream();
        ci.encode(out);
        check(Arrays.equals(out.toByteArray(), enc),
              "round-trip encode mismatch");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
