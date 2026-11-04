/*
 * @test
 * @summary Witup-derived tests for String(byte[], Charset) constructor behavior
 * @run main StringWitupTest
 */

import java.nio.charset.Charset;

public class StringWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Charset utf8 = Charset.forName("UTF-8");

        byte[] ascii = {0x61, 0x62, 0x63};
        check("abc".equals(new String(ascii, utf8)), "ASCII bytes should decode to abc");

        byte[] accented = {(byte) 0xC3, (byte) 0xA9};
        check("\u00e9".equals(new String(accented, utf8)), "UTF-8 bytes should decode to e-acute");

        check(new String(new byte[0], utf8).isEmpty(), "Empty byte array should produce empty string");

        boolean nullBytesThrew = false;
        try {
            new String((byte[]) null, utf8);
        } catch (NullPointerException e) {
            nullBytesThrew = true;
        }
        check(nullBytesThrew, "Null bytes should throw NullPointerException");

        boolean nullCharsetThrew = false;
        try {
            new String(ascii, (Charset) null);
        } catch (NullPointerException e) {
            nullCharsetThrew = true;
        }
        check(nullCharsetThrew, "Null charset should throw NullPointerException");
    }
}
