/*
 * @test
 * @summary Verify PKCS9Attributes.encode writes the caller-supplied tag and the encoded body without the original tag byte
 * @modules java.base/sun.security.pkcs java.base/sun.security.util
 * @run main PKCS9AttributesWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.pkcs.PKCS9Attribute;
import sun.security.pkcs.PKCS9Attributes;

public class PKCS9AttributesWitupTest {

    public static void main(String[] args) throws IOException {
        PKCS9Attributes attrs = new PKCS9Attributes(new PKCS9Attribute[0]);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        attrs.encode((byte) 0x31, out);
        byte[] b = out.toByteArray();
        check(b.length == 2, "expected 2 bytes for empty set, got " + b.length);
        check(b[0] == (byte) 0x31, "first byte must be the supplied tag 0x31");
        check(b[1] == 0x00, "body byte must be 0x00 for empty set");

        out.reset();
        attrs.encode((byte) 0xA0, out);
        b = out.toByteArray();
        check(b.length == 2, "expected 2 bytes with alternate tag, got " + b.length);
        check(b[0] == (byte) 0xA0, "first byte must be the supplied tag 0xA0");
        check(b[1] == 0x00, "body byte must be 0x00 for empty set with alternate tag");

        ByteArrayOutputStream prefixed = new ByteArrayOutputStream();
        prefixed.write(0x7F);
        attrs.encode((byte) 0x31, prefixed);
        b = prefixed.toByteArray();
        check(b.length == 3, "encode must append to existing stream content");
        check(b[0] == 0x7F, "existing prefix byte must be preserved");
        check(b[1] == (byte) 0x31, "tag must follow the existing prefix");
        check(b[2] == 0x00, "body must follow the tag");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
