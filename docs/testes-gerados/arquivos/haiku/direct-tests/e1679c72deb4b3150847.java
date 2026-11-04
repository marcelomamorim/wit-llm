/*
 * @test
 * @summary Verifies PKCS9Attributes.encode writes the given tag byte followed by the stored DER content
 * @modules java.base/sun.security.util java.base/sun.security.pkcs
 * @run main PKCS9AttributesWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.pkcs.PKCS9Attributes;
import sun.security.util.DerInputStream;

public class PKCS9AttributesWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static PKCS9Attributes emptySet() throws IOException {
        byte[] der = new byte[] {0x31, 0x00};
        return new PKCS9Attributes(new DerInputStream(der));
    }

    static void testEncodeEmptySetWritesTagAndContent() throws IOException {
        PKCS9Attributes attrs = emptySet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        attrs.encode((byte) 0x31, out);
        byte[] result = out.toByteArray();
        check(result.length == 2, "expected length 2 but was " + result.length);
        check(result[0] == (byte) 0x31, "unexpected tag byte " + result[0]);
        check(result[1] == (byte) 0x00, "unexpected content byte " + result[1]);
    }

    static void testEncodeUsesProvidedTagByte() throws IOException {
        PKCS9Attributes attrs = emptySet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        attrs.encode((byte) 0xA0, out);
        byte[] result = out.toByteArray();
        check(result.length == 2, "expected length 2 but was " + result.length);
        check(result[0] == (byte) 0xA0, "tag argument not written, got " + result[0]);
        check(result[1] == (byte) 0x00, "content byte changed, got " + result[1]);
    }

    static void testEncodeAppendsToExistingStream() throws IOException {
        PKCS9Attributes attrs = emptySet();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(0x7F);
        attrs.encode((byte) 0x31, out);
        byte[] result = out.toByteArray();
        check(result.length == 3, "expected length 3 but was " + result.length);
        check(result[0] == (byte) 0x7F, "prefix byte overwritten");
        check(result[1] == (byte) 0x31, "tag not appended after prefix");
        check(result[2] == (byte) 0x00, "content not appended after tag");
    }

    public static void main(String[] args) throws Exception {
        testEncodeEmptySetWritesTagAndContent();
        testEncodeUsesProvidedTagByte();
        testEncodeAppendsToExistingStream();
    }
}
