/* @test    @summary Test String(byte[], Charset) constructor for various byte arrays and charsets    @run main StringWitupTest */

import java.lang.String;
import java.nio.charset.Charset;

public class StringWitupTest {

    public static void main(String[] args) {
        testEmptyByteArray();
        testAsciiByteArray();
        testUtf8ByteArray();
        testInvalidCharset();
    }

    static void testEmptyByteArray() {
        byte[] bytes = new byte[0];
        String s = new String(bytes, Charset.defaultCharset());
        assert s.length() == 0 : "Expected empty string";
    }

    static void testAsciiByteArray() {
        byte[] bytes = {65, 66, 67}; // 'A', 'B', 'C'
        String s = new String(bytes, Charset.forName("US-ASCII"));
        assert s.equals("ABC") : "Expected 'ABC', got '" + s + "'";
    }

    static void testUtf8ByteArray() {
        byte[] bytes = {(byte)0xE2, (byte)0x82, (byte)0xAC}; // Euro sign
        String s = new String(bytes, Charset.forName("UTF-8"));
        assert s.equals("€") : "Expected '€', got '" + s + "'";
    }

    static void testInvalidCharset() {
        byte[] bytes = {65, 66, 67};
        try {
            String s = new String(bytes, Charset.forName("Invalid-Charset"));
            assert false : "Expected UnsupportedCharsetException";
        } catch (Exception e) {
            assert e instanceof java.nio.charset.UnsupportedCharsetException : "Expected UnsupportedCharsetException";
        }
    }
}
